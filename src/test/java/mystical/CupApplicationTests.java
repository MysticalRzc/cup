package mystical;

import com.google.gson.Gson;
import com.sun.corba.se.pept.transport.ContactInfo;
import mystical.cup.model.CatchModel;
import mystical.cup.utils.ConvertUtil;
import mystical.cup.utils.RedisUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
