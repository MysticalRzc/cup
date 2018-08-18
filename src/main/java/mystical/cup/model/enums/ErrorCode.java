package mystical.cup.model.enums;

import lombok.Data;

/**
 * Created by Rzc on 2018/9/6.
 */
public enum ErrorCode{
    SUCCESS("0000","成功")

    ,CONTRLLER_NOFOUND("1404","未定义的接口")

    ,PARAM_ERROR("2001","参数错误")
    ,TOCKEN_INVALID("2002","登陆状态失效")
    ,SYSTEM_ERROR("9999","系统错误")
    ;

    private String code;
    private String msg;

    ErrorCode(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
