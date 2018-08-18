package mystical.cup.utils;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rzc on 2018/8/18.
 */
public class ConvertUtil{
    private final static Gson gson = new Gson( );

    public static String toJson(Object obj){
        if(obj != null){
            return gson.toJson(obj);
        }else{
            return null;
        }
    }

    public static <T> T fromJson(Class<T> c,String json){
        T t = gson.fromJson(json,c);
        return t;
    }

    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
