package mystical.cup.model.database;

import lombok.Data;

/**
 * Created by Rzc on 2018/8/13.
 */
@Data
public class ReturnContent{
    private String respCode;
    private String retMsg;
    private Object retData;

    public static ReturnContent success(Object object){
        ReturnContent returnContent = new ReturnContent();
        returnContent.setRespCode("0000");
        returnContent.setRetMsg("success");
        returnContent.setRetData(object);
        return returnContent;
    }
    public static ReturnContent success(){
        return success(null);
    }
}
