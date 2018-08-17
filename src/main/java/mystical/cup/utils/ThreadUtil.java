package mystical.cup.utils;

import com.google.gson.Gson;
import mystical.cup.model.vo.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Rzc on 2018/8/15.
 */
public class ThreadUtil{
    private static final Logger logger = LoggerFactory.getLogger(ThreadUtil.class);

    private static final int EXECUTOR_WHITE_TIME = 30;
    private static final int THREAD_POOL_MAX = 10;

    private static ExecutorService executorService = null;

    private static final Map<String, Map<String, Future<ThreadMode>>> threadResult = new Hashtable<>( );
    private static final Map<String, Map<String, List<String>>> threadError = new Hashtable<>( );

    public static void executeThread(String groupGid, String threadGid, Thread thread){
        if(executorService == null){
            executorService = Executors.newFixedThreadPool(THREAD_POOL_MAX);
        }
        executorService.execute(thread);
    }

    public static void submitThread(String groupGid, String threadGid, Callable thread){
        if(executorService == null){
            executorService = Executors.newFixedThreadPool(THREAD_POOL_MAX);
        }
        Future<ThreadMode> future = executorService.submit(thread);
        addResult(groupGid, threadGid, future);
    }

    private static void addResult(String groupGid, String threadGid, Future<ThreadMode> future){
        if(!threadResult.containsKey(groupGid)){
            Map<String, Future<ThreadMode>> threadMap = new Hashtable<>( );
            threadResult.put(groupGid, threadMap);
        }
        if(threadResult.get(groupGid).containsKey(threadGid)){
            logger.info("Thread exe result will be covered result={}", threadResult.get(groupGid).get(threadGid).toString());
        }
        threadResult.get(groupGid).put(threadGid, future);
    }

    public static Future<ThreadMode> getResult(String groupGid, String threadGid){
        if(!threadResult.containsKey(groupGid)){
            return null;
        }
        return threadResult.get(groupGid).get(threadGid);
    }

    public static Map<String, Future<ThreadMode>> getResultByGroup(String groupGid){
        if(threadResult.containsKey(groupGid)){
            return threadResult.get(groupGid);
        }
        return null;
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

    synchronized public static void setThreadError(String groupGid, String threadGid, String errorData){
        if(!threadError.containsKey(groupGid)){
            Map<String, List<String>> threadErrorMap = new Hashtable<>();
            threadError.put(groupGid,threadErrorMap);
        }
        if(!threadError.get(groupGid).containsKey(threadGid)){
            threadError.get(groupGid).put(threadGid,new ArrayList<>());
        }
        threadError.get(groupGid).get(threadGid).add(errorData);
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
