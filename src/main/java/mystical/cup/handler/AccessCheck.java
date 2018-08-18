package mystical.cup.handler;

import java.lang.annotation.*;

/**
 * Created by Rzc on 2018/9/10.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AccessCheck{
    String requestUrl();
}
