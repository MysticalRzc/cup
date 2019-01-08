package mystical.cup.model.vo.requestVO;

import lombok.Data;
import mystical.cup.model.ReturnContent;
import mystical.cup.model.enums.ErrorCode;
import mystical.cup.utils.VerifyUtil;

/**
 * Created by Rzc on 2018/10/31.
 */
@Data
public class ConsumerConfigReq{
    private Integer id;
    private String requestUrl;
    private String requestParam;
    private String responseParam;
    private String requestType;
    private String desc;
    private String ak;
    private String sk;
    private String creator;
    private String tocken;

    public ReturnContent checkRequestParam_update(){
        if(id == null || id <=0){
            return ReturnContent.error(ErrorCode.PARAM_ERROR,"id 不能为空并且大于零");
        }else if(VerifyUtil.isBlank(requestUrl) || VerifyUtil.isBlank(requestParam) || VerifyUtil.isBlank(responseParam)){
            return ReturnContent.error(ErrorCode.PARAM_ERROR, "请求url 和 输入输出参数不能为空");
        }else if(VerifyUtil.isBlank(creator) || VerifyUtil.isBlank(tocken)){
            return ReturnContent.error(ErrorCode.TOCKEN_INVALID);
        }
        return ReturnContent.success();
    }

    public ReturnContent checkRequestParam_Add(){
        if(VerifyUtil.isBlank(requestUrl) || VerifyUtil.isBlank(requestParam) || VerifyUtil.isBlank(responseParam)){
            return ReturnContent.error(ErrorCode.PARAM_ERROR, "请求url 和 输入输出参数不能为空");
        }else if(VerifyUtil.isBlank(creator) || VerifyUtil.isBlank(tocken)){
            return ReturnContent.error(ErrorCode.TOCKEN_INVALID);
        }
        return ReturnContent.success();
    }

    public ReturnContent checkRequestParam_Delete(){
        if(id == null || id <=0){
            return ReturnContent.error(ErrorCode.PARAM_ERROR,"id 不能为空并且大于零");
        }
        return ReturnContent.success();
    }
}
