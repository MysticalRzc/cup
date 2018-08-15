package mystical.trash;

import org.junit.Test;

/**
 * Created by Rzc on 2018/8/14.
 */
public class ExceptionTest{
    @Test
    public void tests(){
        try{
            throw new Exception();
        }catch(Exception e){
            e.printStackTrace( );
            int a = 2/0;
        }

    }
}
