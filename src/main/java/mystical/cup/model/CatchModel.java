package mystical.cup.model;

import lombok.Data;

/**
 * Created by Rzc on 2018/8/18.
 */
@Data
public class CatchModel{
    private String requestURL;
    private String requestData;

    private String requestParam;
    private String responseParam;

    private String responseTime;
    private String requestTime;
}
