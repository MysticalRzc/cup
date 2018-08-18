package mystical.cup.model;

import lombok.Data;
import mystical.cup.model.enums.ErrorCode;

import java.awt.print.Pageable;

/**
 * Created by Rzc on 2018/8/13.
 */
@Data
public class ReturnContent{
    private String retCode;
    private String retMsg;
    private Object retData;

    public static ReturnContent success(Object object){
        ReturnContent returnContent = new ReturnContent( );
        returnContent.setRetCode("0000");
        returnContent.setRetMsg("success");
        returnContent.setRetData(object);
        return returnContent;
    }

    public static ReturnContent error(ErrorCode errorCode, String retMsg){
        ReturnContent returnContent = new ReturnContent( );
        returnContent.setRetCode(errorCode.getCode( ));
        if(retMsg != null){
            returnContent.setRetMsg(retMsg);
        }else{
            returnContent.setRetMsg(errorCode.getMsg( ));
        }
        returnContent.setRetData(null);
        return returnContent;
    }

    public static ReturnContent error(ErrorCode errorCode){
        return error(errorCode,null);
    }

    public static ReturnContent success(){
        return success(null);
    }
    public Boolean isSuccess(){
        if(ErrorCode.SUCCESS.getCode().equals(this.retCode)){
            return true;
        }
        return false;
    }
}
