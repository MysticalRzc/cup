package mystical.cup;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rzc on 2018/8/13.
 */
public class ThreadPoolTest{
    public static int count = 0;

    @Test
    public void test1(){
        ExecutorService es = Executors.newCachedThreadPool ();
        for(int i = 0; i < 10; i++){
            Taskdemo tc = new Taskdemo( );
            es.execute(tc);
        }
        int threadCount = ((ThreadPoolExecutor)es).getActiveCount();
        while(true){
            System.out.println(threadCount );
            try{
                Thread.sleep(50);
            }catch(InterruptedException e){
                e.printStackTrace( );
            }
        }
    }

        @Test
    public void test(){
        ExecutorService es = Executors.newFixedThreadPool(2);
        for(int i = 0; i < 10; i++){
            Taskdemo tc = new Taskdemo( );
            es.execute(tc);
        }

        try{
            es.shutdown( );
            if(!es.awaitTermination(30, TimeUnit.MILLISECONDS)){
                es.shutdownNow( );
            }
        }catch(InterruptedException e){
            es.shutdownNow( );
        }
        System.out.println("is shout Down !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" );
        System.out.println("is shout Down !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" );
        System.out.println("is shout Down !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" );
        System.out.println("is shout Down !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" );
        System.out.println("is shout Down !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" );
//        try{
//            Thread.sleep(5000);
//            System.out.println(count + "<<<<" );
//        }catch(InterruptedException e){
//            e.printStackTrace( );
//        }
    }

    public static class Taskdemo implements Runnable{

        @Override
        public void run(){
            for(int i = 0; i < 10; i++){
                System.out.println(Thread.currentThread( ).getName( ) + ":" + i);
                count++;
                try{
                    Thread.sleep(50);
                }catch(InterruptedException e){
                    e.printStackTrace( );
                }
            }
        }
    }
}
