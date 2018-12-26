package mystical.cup.utils.akskUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import mystical.cup.model.database.ConfigController;
import mystical.cup.utils.ContextHoldler;
import mystical.cup.utils.VerifyUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rzc on 2018/10/3.
 */
@Slf4j
public class AkSkUtil{

    public final static String WESHAREAUTH_TOKEN = "X-WeshareAuth-AccessCheck";

    public static boolean checkAkSk(ConfigController ctl, String tokenType){
        switch(tokenType){
            case "weshare":
                return checkWeshareAkSk(ctl);
        }

        return false;
    }

    private static boolean checkWeshareAkSk(ConfigController ctl){
        log.debug("===>check access token start");
        ProceedingJoinPoint joinPoint = (ProceedingJoinPoint) ContextHoldler.get("aroundAOP","proceedingJointPoint");
        System.out.println(joinPoint == null );
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try{
            request.setCharacterEncoding("UTF-8");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace( );
        }
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        String requestURI = request.getServletPath();
        Integer isToken = 1;

        boolean checkToken = true;
        if (isToken == 1) {
            String token = request.getHeader(WESHAREAUTH_TOKEN);
            log.debug("CheckRequestHead:  token = {}", token);
            if (VerifyUtil.isBlank(token)) {
                log.error("CheckRequestHead:  Weshare-Auth-Token is empty");
                return false;
            }

            String ak = ctl.getSk();
            String sk = ctl.getAk();

            String requestMethod = request.getMethod();
            String queryParam = request.getQueryString();

            Object[] args = joinPoint.getArgs();
            Object bodyArg = null;
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] == null) {
                    continue;
                }
                if (parameters[i].isAnnotationPresent(RequestBody.class)) {
                    bodyArg = args[i];
                    break;
                }
            }

            String requestBody = null;
            if (bodyArg != null) {
                requestBody = new GsonBuilder().disableHtmlEscaping().create().toJson(bodyArg);
            }
            log.debug("===>get the equest url:{}; and request method:{}", requestURI, requestMethod);

            WeshareTokenHelper helper = new WeshareTokenHelper(ak, sk);
            checkToken = helper.verifyToken(token, requestURI, requestMethod, queryParam, requestBody);
        }
        return false;
    }

//    public static boolean checkWeshareAkSk(ConfigController ctl){
//        ProceedingJoinPoint joinPoint = (ProceedingJoinPoint) ContextHoldler.get("around","proceedingJointPoint");
//        log.debug("===>check access token start");
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes( )).getRequest( );
//        try{
//            request.setCharacterEncoding("UTF-8");
//        }catch(UnsupportedEncodingException e){
//            e.printStackTrace( );
//        }
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes( )).getResponse( );
//
//        String requestURI = request.getServletPath( );
//        boolean checkToken = true;
//        String token = request.getHeader(WESHAREAUTH_TOKEN);
//        log.debug("CheckRequestHead:  token = {}", token);
//        if(VerifyUtil.isBlank(token)){
//            log.warn("CheckRequestHead:  Weshare-Auth-AccessCheck is empty");
//            return false;
//        }
//
//        String[] listAterTokenSplit = token.split("-");
//        String ak = ctl.getAk();
//        String sk = ctl.getSk();
//
//        String requestMethod = request.getMethod( );
//        String queryParam = request.getQueryString( );
//        Object[] args = joinPoint.getArgs( );
//        Object bodyArg = null;
//        Method method = ((MethodSignature) joinPoint.getSignature( )).getMethod( );
//        Parameter[] parameters = method.getParameters( );
//        for(int i = 0; i < parameters.length; i++){
//            if(parameters[i] == null){
//                continue;
//            }
//            if(parameters[i].isAnnotationPresent(RequestBody.class)){
//                bodyArg = args[i];
//                break;
//            }
//        }
//
//        String requestBody = null;
//        if(bodyArg != null){
//            requestBody = new GsonBuilder( ).disableHtmlEscaping( ).create( ).toJson(bodyArg);
//        }
//        log.debug("===>get the equest url:{}; and request method:{}", requestURI, requestMethod);
//
//        WeshareTokenHelper helper = new WeshareTokenHelper(ak, sk);
//        checkToken = helper.verifyToken(token, requestURI, requestMethod, queryParam, requestBody);
//        return false;
//    }
}
