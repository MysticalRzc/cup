package mystical.trash;

import java.util.*;

/**
 * Created by Rzc on 2018/8/13.
 */
public class Tests{
    List<Map<String, String>> data = new LinkedList<>( );

    public void test(){
        init( );
        for1( );
        for2( );
        for3( );
    }

    public void for1(){
        long beginTime = System.currentTimeMillis( );
        System.out.println("begin for1 " + new Date( ).toString( ));
        for(int i = 0; i < data.size( ); i++){
            String str = data.get(i).get("key");
        }
        System.out.println("end for1 " + new Date( ).toString( ) + "=====>>> useTime:" + (System.currentTimeMillis( ) - beginTime));
    }

    public void for2(){
        long beginTime = System.currentTimeMillis( );
        System.out.println("begin for2 " + new Date( ).toString( ));
        for(Map<String, String> map : data){
            String str = map.get("key");
        }
        System.out.println("end for2 " + new Date( ).toString( ) + "=====>>> useTime:" + (System.currentTimeMillis( ) - beginTime));
    }

    public void for3(){
        long beginTime = System.currentTimeMillis( );
        System.out.println("begin for3 " + new Date( ).toString( ));
        data.forEach(e -> {
            String str = e.get("key");
        });
        System.out.println("end for3 " + new Date( ).toString( ) + "=====>>> useTime:" + (System.currentTimeMillis( ) - beginTime));
    }

    public void init(){
        long beginTime = System.currentTimeMillis( );
        System.out.println("begin init" + new Date( ).toString( ));
        int count = 0;
        while(count < 50000){
            Map<String, String> map = new HashMap<>( );
            map.put("key", "value" + count);
            data.add(map);
            count ++;
        }
        System.out.println("finish init" + new Date( ).toString( ) + "=====>>> useTime:" + (System.currentTimeMillis( ) - beginTime));
    }
}
