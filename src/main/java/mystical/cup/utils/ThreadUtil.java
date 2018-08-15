package mystical.cup.utils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rzc on 2018/8/15.
 */
public class ThreadUtil{
    private static final Logger logger = LoggerFactory.getLogger(ThreadUtil.class);

    private static final int EXECUTOR_WHITE_TIME = 30;
    private static final int THREAD_POOL_MAX = 10;
    private static ExecutorService executorService = null;

    private static final Map<String, List<String>> threadError = new Hashtable<>( );

    public static void executeThread(String executorGId, Thread thread){
        if(executorService == null){
            executorService = Executors.newFixedThreadPool(THREAD_POOL_MAX);
        }
        executorService.execute(thread);
    }
    public static void submitThread(String executorGid,Thread thread){
        if(executorService == null){
            executorService = Executors.newFixedThreadPool(THREAD_POOL_MAX);
        }
        executorService.submit(thread);
    }

    public static void executorShutDown(){
        if(executorService != null){
            try{
                executorService.shutdown( );
                if(!executorService.awaitTermination(EXECUTOR_WHITE_TIME, TimeUnit.MILLISECONDS)){
                    executorService.shutdownNow( );
                }
            }catch(InterruptedException e){
                logger.info("awaitTermination interrupted: " + e);
                executorService.shutdownNow( );
            }
        }
    }

    public static Object getThreadError(){
        Map map = new HashMap( );
        map.put("当前错误日志", new Gson( ).toJson(threadError));
        return map.toString( );
    }

    public static Object removeError(String threadTypeGid){
        if(threadError.containsKey(threadTypeGid)){
            threadError.remove(threadTypeGid);
            return "remove success";
        }
        return "remove fail, gid no found";
    }


    public static String getThreadError(String threadTypeGid){
        if(threadError.containsKey(threadTypeGid)){
            if(threadError.get(threadTypeGid).size( ) > 0){
                String errorCode = new Gson( ).toJson(threadError.get(threadTypeGid));
                return errorCode;
            }
        }
        return null;
    }
}
