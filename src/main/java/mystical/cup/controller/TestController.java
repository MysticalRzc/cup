package mystical.cup.controller;

import mystical.cup.model.CatchModel;
import mystical.cup.utils.ConvertUtil;
import mystical.cup.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;
import java.util.Map;

import static mystical.cup.config.ProjectConfig.REDIS_CONTROLLER_CATCH_LIST;

/**
 * Created by Rzc on 2018/8/18.
 */
@RestController
public class TestController{

    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/test")
    public String test(){
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
        return "success";
    }
    @RequestMapping(value="userCach/clear",method = RequestMethod.POST)
    public String updateUserCache(String channel){
        return "success";
    }
}
