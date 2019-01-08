package mystical;

import com.google.gson.Gson;
import com.sun.corba.se.pept.transport.ContactInfo;
import mystical.cup.model.CatchModel;
import mystical.cup.utils.ConvertUtil;
import mystical.cup.utils.RedisUtil;
import mystical.cup.utils.Utility;
import mystical.test.TestService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CupApplicationTests{


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
        TestService testService = new TestService();
        try{
            new BigDecimal(2).multiply(new BigDecimal(2));
            testService.testGenerateToken();
        }catch(Exception e){
            e.printStackTrace( );
        }
    }
}

