package mystical.cup.model;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by Rzc on 2018/8/18.
 */
@Data
public class CatchModel{
    private String gid;

    private String requestURL;
    private String requestData;
    private String requestParam;
    private String requestType;
    private String requestAddress;

    private String responseParam;
    private long responseTime;
    private long requestTime;

    private Object Exception;
}
