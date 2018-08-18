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

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CupApplicationTests{

    @Resource(name="catchModels")
    private CatchModel catchModeld;


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
        System.out.println(catchModeld.getGid() );
        System.out.println(new Gson().toJson(catchModeld) );
    }

}

