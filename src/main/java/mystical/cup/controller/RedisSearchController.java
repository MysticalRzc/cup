package mystical.cup.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rzc on 2018/8/23.
 */
@RestController
@RequestMapping("/redis")
public class RedisSearchController{

    Logger logger = LoggerFactory.getLogger(RedisSearchController.class);

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/search")
    public Object searchRedis(String key){
        logger.info("redis search like has invoke");
        return redisTemplate.opsForValue( ).get(key);
    }

    @RequestMapping("/search/like")
    public Object setLikeRedis(String keys){
        logger.info("redis search like has invoke");
        return redisTemplate.keys("*" + keys + "*");
    }

    @RequestMapping("/set")
    public void setRedis(String key,String value){
        logger.info("redis set has invoke");
        redisTemplate.opsForValue( ).set(key,value);
    }

    @RequestMapping("/delete")
    public void deleteRedis(String key){
        logger.info("redis delete has invoke");
        redisTemplate.delete(key);
    }

    @RequestMapping("/delete/like")
    public void deleteLikeRedis(String keys){
        logger.info("redis delete like has invoke");
        redisTemplate.delete(redisTemplate.keys("FKN:Brain:userTranInfoCache:" + keys + "*"));
    }
}
