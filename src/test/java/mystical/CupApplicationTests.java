package mystical;

import com.google.gson.Gson;
import com.sun.corba.se.pept.transport.ContactInfo;
import mystical.cup.model.CatchModel;
import mystical.cup.utils.ConvertUtil;
import mystical.cup.utils.RedisUtil;
import mystical.cup.utils.Utility;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static mystical.cup.config.ProjectConfig.REDIS_CONTROLLER_CATCH_LIST;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CupApplicationTests{

    @Autowired
    RedisUtil redisUtil;

    @Before
    public void init(){
        System.out.println("--------------开始测试---------------");
    }

    @After
    public void after(){
        System.out.println("--------------测试结束---------------");
    }

    @Test
    public void test(){
        CatchModel catchModel = new CatchModel( );
        catchModel.setRequestURL("aaa");

        System.out.println(REDIS_CONTROLLER_CATCH_LIST);

        for(int i = 0; i < 10; i++){
            catchModel.setRequestURL("aaa" + i);
            long begin = System.currentTimeMillis( );
            long length = redisUtil.queuePush(REDIS_CONTROLLER_CATCH_LIST, ConvertUtil.toJson(catchModel));
            System.out.println("use time " + (System.currentTimeMillis( ) - begin));
            System.out.println(length);
        }
        try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            e.printStackTrace( );
        }
//        System.out.println(redisUtil.queuePop(REDIS_CONTROLLER_CATCH_LIST));
//        System.out.println(ConvertUtil.fromJson(CatchModel.class,redisUtil.queuePop(REDIS_CONTROLLER_CATCH_LIST)));

        //        CatchModel catchModel1 = new Gson( ).fromJson(redisUtil.queuePop(REDIS_CONTROLLER_CATCH_LIST), CatchModel.class);
    }

    @Test
    public void redisTest(){
        redisUtil.set("hello", "world");
    }

    @Test
    public void test03(){

        String KEY = "FKN:Brain:userTranInfoCache:sdjk:";
        Set<String> keys = redisTemplate.keys(KEY + "");
        System.out.println(redisUtil.deleteByLike(keys) );
        for(int i = 0; i < 10; i++){
            redisUtil.set(KEY + i,"" + i);
        }
        for(int i = 0;i< 10; i++){
            System.out.println(redisUtil.get(KEY + i));
        }
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
            e.printStackTrace( );
        }
//        Set<String> keys = redisTemplate.keys(KEY + "");
//        System.out.println(redisUtil.deleteByLike(keys) );
    }
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void redisTimeTest(){
        redisTemplate.opsForValue().set("timeKey","timeValue");
        redisTemplate.expireAt("timeKey",Utility.getToday23HourDate());
        System.out.println(redisTemplate.getExpire("timeKey") );
    }

    @Test
    public void redisReturnTest(){
        redisTemplate.opsForValue().set("cacheVersion","1_"+ System.currentTimeMillis());
    }

    @Test
    public void utilTest(){
        System.out.println(ConvertUtil.stampToDate(System.currentTimeMillis()+"") );
    }
}

