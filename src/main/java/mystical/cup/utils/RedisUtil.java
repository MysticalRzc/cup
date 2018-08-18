package mystical.cup.utils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;

/**
 * Created by Rzc on 2018/3/6.
 */

@Component
public final class RedisUtil{

    Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    public boolean set(final String key, Object value){
        boolean result = false;
        try{
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue( );
            operations.set(key, value);
            result = true;
        }catch(Exception e){
            e.printStackTrace( );
        }
        return result;
    }

    public Long queuePush(final String key, String value){
        long length = -1;
        if(VerifyUtil.isBlank(key)){
            logger.info("redis queue push key is null key={}",key);
            return length;
        }
        try{
            ListOperations<String, String> operations = redisTemplate.opsForList( );
            length = operations.rightPush(key, value);
            return length;
        }catch(Exception e){
            logger.error("redis list right push failed", e);
        }
        return length;
    }

    public String queuePop(final String key){
        if(VerifyUtil.isBlank(key)){
            logger.info("redis queue push key is null key={}",key);
            return null;
        }
        try{
            ListOperations<String, String> operations = redisTemplate.opsForList( );
            String result = operations.leftPop(key);
            return result;
        }catch(Exception e){
            logger.error("redis list left pop failed", e);
        }
        return null;
    }
}
