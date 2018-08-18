package mystical.cup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Mystical on 2018/2/25.
 */
@Component
public class ProjectConfig{
    //server info
    public static int POST;
    public static boolean SSL;

    //redis Name
    public static String REDIS_AREA_NAME = "Quickly:Cup:";

    public static String  REDIS_CONTROLLER_CATCH_LIST_POST = REDIS_AREA_NAME + "CtlCatch:Post:";
    public static String  REDIS_CONTROLLER_CATCH_LIST_GET = REDIS_AREA_NAME + "CtlCatch:Get:";
}