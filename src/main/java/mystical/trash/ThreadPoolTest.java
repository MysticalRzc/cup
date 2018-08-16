package mystical.trash;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Rzc on 2018/8/13.
 */
public class ThreadPoolTest{
    public static int count = 0;

    @Test
    public void test1(){
        Map map=Thread.getAllStackTraces();
        System.out.println( "============>>>>" +map.size());
        ExecutorService es = Executors.newFixedThreadPool (10);
        List<Future<String>> resultList = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            Callable tc = new Taskdemo( );
            Future<String> future = es.submit(tc);
            resultList.add(future);
        }
        boolean exit = false;
        while(true){
            System.out.println("===========================================================================" );
            for(Future<String> fs : resultList){
                try{
                    System.out.println("线程执行结果:" + fs.get( ));
                }catch(InterruptedException e){
                    e.printStackTrace( );
                }catch(ExecutionException e){
                    e.printStackTrace( );
                }finally{
                    es.shutdown();
                }
            }
            if(exit)
                break;
            if(resultList.size() == 10)
                exit = true;
            try{
                Thread.sleep(10);
            }catch(InterruptedException e){
                e.printStackTrace( );
            }
        }
//        int threadCount = ((ThreadPoolExecutor)es).getActiveCount();
//        while(true){
//            map=Thread.getAllStackTraces();
//            System.out.println( "============>>>>" +map.size());
//            System.out.println(threadCount );
//            try{
//                Thread.sleep(50);
//            }catch(InterruptedException e){
//                e.printStackTrace( );
//            }
//        }
    }


    public static class Taskdemo implements Callable<String>{

        @Override
        public String call(){
            for(int i = 0; i < 10; i++){
                System.out.println(Thread.currentThread( ).getName( ) + ":" + i);
                count++;
                try{
                    Thread.sleep(50);
                }catch(InterruptedException e){
                    e.printStackTrace( );
                }
            }
            return "success";
        }
    }
}
