package mystical.cup.service;

import mystical.cup.dao.mapper.ConfigControllerMapper;
import mystical.cup.model.ReturnContent;
import mystical.cup.model.database.ConfigController;
import mystical.cup.model.enums.ErrorCode;
import mystical.cup.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Rzc on 2018/11/2.
 */
@Service
public class ControllerConfigService{
    @Autowired
    private ConfigControllerMapper configControllerMapper;

    public ReturnContent updateController(ConfigController controllerConfig){

        if(configControllerMapper.updateByPrimaryKeySelective(controllerConfig) < 0){
            return ReturnContent.error(ErrorCode.SYSTEM_ERROR,"更新数据库字段失败");
        }
        return ReturnContent.success();
    }

    public ReturnContent addController(ConfigController controllerConfig){

        if(configControllerMapper.insertSelective(controllerConfig)< 0){
            return ReturnContent.error(ErrorCode.SYSTEM_ERROR,"添加数据库字段失败");
        }
        return ReturnContent.success();
    }
    public ReturnContent deleteController(ConfigController controllerConfig){

        if(configControllerMapper.deleteByPrimaryKey(controllerConfig.getId())< 0){
            return ReturnContent.error(ErrorCode.SYSTEM_ERROR,"删除数据库字段失败");
        }
        return ReturnContent.success();
    }
}
