package mystical.cup.utils.akskUtil;

import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import mystical.cup.model.database.HttpConsumerMode;
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

    public static boolean checkAkSk(HttpConsumerMode httpConsumerMode, String tokenType){
        switch(tokenType){
            case "weshare":
                return checkWeshareAkSk(httpConsumerMode);
        }

        return false;
    }

    private static boolean checkWeshareAkSk(HttpConsumerMode httpConsumerMode){
        ProceedingJoinPoint joinPoint = (ProceedingJoinPoint) ContextHoldler.get("aroundAOP", "proceedingJointPoint");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes( )).getRequest( );
        try{
            request.setCharacterEncoding("UTF-8");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace( );
        }
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes( )).getResponse( );

        String requestURI = request.getServletPath( );
        Integer isToken = 1;

        if(isToken == 1){
            String token = request.getHeader(WESHAREAUTH_TOKEN);
            if(VerifyUtil.isBlank(token)){
                log.error("CheckRequestHead:  Weshare-Auth-Token is empty");
                return false;
            }

            String ak = httpConsumerMode.getAk( );
            String sk = httpConsumerMode.getSk( );

            String requestMethod = request.getMethod( );
            String queryParam = request.getQueryString( );

            Object[] args = joinPoint.getArgs( );
            Object bodyArg = null;
            Method method = ((MethodSignature) joinPoint.getSignature( )).getMethod( );
            Parameter[] parameters = method.getParameters( );
            for(int i = 0; i < parameters.length; i++){
                if(parameters[i] == null){
                    continue;
                }
                if(parameters[i].isAnnotationPresent(RequestBody.class)){
                    bodyArg = args[i];
                    break;
                }
            }

            String requestBody = null;
            if(bodyArg != null){
                requestBody = new GsonBuilder( ).disableHtmlEscaping( ).create( ).toJson(bodyArg);
            }
            WeshareTokenHelper helper = new WeshareTokenHelper(ak, sk);
            return helper.verifyToken(token, requestURI, requestMethod, queryParam, requestBody);
        }
        return false;
    }
}