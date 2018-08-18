package mystical.cup.utils;

/**
 * Created by Rzc on 2018/8/16.
 */
public class VerifyUtil{
    //比较确认工具
    public static boolean isBlank(String str){
        if(str == null || "".equals(str)){
            return true;
        }
        return false;
    }
}