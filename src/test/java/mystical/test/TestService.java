package mystical.test;

import mystical.cup.utils.akskUtil.WeshareTokenHelper;
import org.junit.Test;

/**
 * Created by Rzc on 2018/12/25.
 */
public class TestService{
    WeshareTokenHelper weshareTokenHelper = new WeshareTokenHelper("y3tjSQCO3yS6uY3M", "6cAvDqw5ZSXyG6KLYSXAfFx7D0bT4den");

    @Test
    public void testGenerateToken() throws Exception {
        String str = weshareTokenHelper.generateToken("/storm/v1/usser", "POST", "uid=3&gid=xxxxx","{\"name\": \"Tom\", \"age\": 3552}", (int)(System.currentTimeMillis()/1000));
        System.out.println(str);
        testVerifyToken(str);
    }

    public void testVerifyToken(String token) throws Exception {
        if (weshareTokenHelper.verifyToken(token, "/storm/v1/usser", "POST", "uid=3&gid=xxxxx",
                "{\"name\": \"Tom\", \"age\": 32}")) {
            System.out.println("pass token verification");
        } else {
            System.out.println("bad token");
        }
    }
}
