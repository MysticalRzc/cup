package mystical.cup.utils;


import java.util.Random;
import java.util.UUID;

/**
 * Created by Rzc on 2018/8/16.
 */
public class RandomUtil{
    public static String randomUUID(){
        return UUID.randomUUID().toString();
    }

    public static String randomGid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
