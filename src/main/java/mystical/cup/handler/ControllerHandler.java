package mystical.cup.handler;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import mystical.cup.config.ProjectConfig;
import mystical.cup.model.CatchModel;
import mystical.cup.utils.ContextHoldler;
import mystical.cup.utils.RandomUtil;
import mystical.cup.utils.RedisUtil;
import mystical.cup.utils.akskUtil.AkSkUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.UUID;


/**
 * Created by Rzc on 2018/9/6.
 */
@Slf4j
@Aspect
@Component
public class ControllerHandler{
    Logger logger = LoggerFactory.getLogger(ControllerHandler.class);
    private static Gson gson = new Gson( );

    private String POST_METHOD = "POST";
    private String GET_METHOD = "GET";

    @Autowired
    private RedisUtil redisUtil;

    @Pointcut(value = "@annotation(mystical.cup.handler.AccessCheck)")
    private void pointcut(){

    }

    @Around(value = "pointcut() && @annotation(accessCheck)")
    public Object around(ProceedingJoinPoint point, AccessCheck accessCheck){
        resetThreadName();
        logger.debug("===> Around AOP start");
        ContextHoldler.put("aroundAOP","proceedingJointPoint",point);
        CatchModel catchModel = new CatchModel( );
        initCatchMode(catchModel);
        logger.debug("catchModel = {}", catchModel);

        try{
            Object returnData = point.proceed( );
            statisticCatchMode(catchModel, returnData);
            return returnData;
        }catch(Throwable throwable){
            throwable.printStackTrace( );
            return throwable.getMessage( );
        }finally{
            logger.debug("<=== Around AOP end catchModel={}", new Gson( ).toJson(catchModel));
            if(POST_METHOD.equals(catchModel.getRequestType( ))){
                redisUtil.queuePush(ProjectConfig.REDIS_CONTROLLER_CATCH_LIST_POST, gson.toJson(catchModel));
            }else if(GET_METHOD.equals(catchModel.getRequestType( ))){
                redisUtil.queuePush(ProjectConfig.REDIS_CONTROLLER_CATCH_LIST_GET, gson.toJson(catchModel));
            }else{
                logger.info("unknow post catchModel={}", gson.toJson(catchModel));
            }
            reBackThreadName();
        }
    }

    @AfterThrowing(value = "pointcut() && @annotation(accessCheck)", throwing = "ex")
    public void afterThrowing(AccessCheck accessCheck, Exception ex){
        System.out.println("after throwing is running");
    }

    public CatchModel initCatchMode(CatchModel catchModel){
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes( )).getRequest( );
            request.setCharacterEncoding("UTF-8");

            catchModel.setGid(RandomUtil.randomGid( ));
            catchModel.setRequestURL(request.getRequestURL( ).toString( ));
            catchModel.setRequestTime(System.currentTimeMillis( ));
            catchModel.setRequestType(request.getMethod( ));
            catchModel.setRequestData(request.getQueryString( ));
            catchModel.setRequestAddress(request.getRemoteAddr( ));

        }catch(UnsupportedEncodingException e){
            e.printStackTrace( );
        }
        return catchModel;
    }

    public void statisticCatchMode(CatchModel catchModel, Object returnData){
        catchModel.setResponseTime(System.currentTimeMillis( ));
        catchModel.setResponseParam(gson.toJson(returnData));
    }

    private void resetThreadName(){
        logger.debug("resetThread ThreadName={}", Thread.currentThread( ).getName( ));
        String[] threadName = Thread.currentThread( ).getName( ).split("&");
        Thread.currentThread( ).setName(threadName[0] + "&" + RandomUtil.randomGid( ));
        logger.debug("resetThread ThreadName={}", Thread.currentThread( ).getName( ));
    }
    private void reBackThreadName(){
        logger.debug("resetThread ThreadName={}", Thread.currentThread( ).getName( ));
        String[] threadName = Thread.currentThread( ).getName( ).split("&");
        Thread.currentThread( ).setName(threadName[0]);
        logger.debug("resetThread ThreadName={}", Thread.currentThread( ).getName( ));
    }
}
