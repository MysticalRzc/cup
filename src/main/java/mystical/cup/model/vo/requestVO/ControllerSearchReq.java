package mystical.cup.model.vo.requestVO;

import lombok.Data;

/**
 * Created by Rzc on 2018/11/27.
 */
@Data
public class ControllerSearchReq{
    private String controllerUrl;
    private String creator;
    private String beginTime;
    private String endTime;
}
