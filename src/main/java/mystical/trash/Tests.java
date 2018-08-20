package mystical.trash;

import com.google.gson.Gson;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Rzc on 2018/8/13.
 */
public class Tests{
    List<Map<String, String>> data = new LinkedList<>( );

    @Test
    public void getTimeStep(){
        Calendar cld = Calendar.getInstance();
        cld.clear();
        Date data = new Date();
        cld.set(data.getYear(),data.getMonth(),data.getDate());
        cld.add(Calendar.DATE,1);
        System.out.println(cld.getTime() );
    }

    @Test
    public void test01(){
        String rate = "[{debit_defq:3.0,rate_defq:0.02},{debit_defq:6.0,rate_defq:0.0175}]";
//        List<RateTerm> installments = Collections.emptyList( );
//        List<Map<String,Double>> rateList = new ArrayList<>();
//        rateList = new Gson().fromJson(rate,List.class);
//        System.out.println(rateList );

        List<RateTerm> installments = new ArrayList<>();
        List<Map<String, Double>> rateList = new ArrayList<>( );
        rateList = new Gson( ).fromJson(rate, List.class);
        for(Map<String,Double> map : rateList){
            RateTerm rateTerm = new RateTerm( );
//                            国内的目前没有多期天数
//                            rateTerm.setPeriod(map.get("day_defq").intValue());
            rateTerm.setTerm(map.get("debit_defq").intValue());
            rateTerm.setRate(new BigDecimal(map.get("rate_defq")));
            installments.add(rateTerm);
        }
    }

    @Test
    public void test2(){
        String userGid = "4a021694b0094672b859ab40a0920752";
        System.out.println(userGid.equals("4a021694b0094672b859ab40a0920752"));
    }

    @lombok.Data
    public static class RateTerm{
        private Integer term;
        private BigDecimal rate;
        private Integer period;
    }
//    @Test
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
