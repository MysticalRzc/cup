package mystical.cup.service;

import mystical.cup.controller.HttpProducer;
import mystical.cup.dao.mapper.HttpConsumerMapper;
import mystical.cup.dao.mapper.HttpProducerMapper;
import mystical.cup.model.ReturnContent;
import mystical.cup.model.database.HttpConsumerMode;
import mystical.cup.model.database.HttpProducerMode;
import mystical.cup.model.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Rzc on 2018/11/2.
 */
@Service
public class ProductorConfigService{
    @Autowired
    private HttpProducerMapper httpProducerMapper;

    public ReturnContent updateController(HttpProducerMode httpProducerMode){

        if(httpProducerMapper.updateByPrimaryKeySelective(httpProducerMode) < 0){
            return ReturnContent.error(ErrorCode.SYSTEM_ERROR,"更新数据库字段失败");
        }
        return ReturnContent.success();
    }

    public ReturnContent addController(HttpProducerMode httpProducerMode){

        if(httpProducerMapper.insertSelective(httpProducerMode)< 0){
            return ReturnContent.error(ErrorCode.SYSTEM_ERROR,"添加数据库字段失败");
        }
        return ReturnContent.success();
    }
    public ReturnContent deleteController(HttpProducerMode httpProducerMode){

        if(httpProducerMapper.deleteByPrimaryKey(httpProducerMode.getId())< 0){
            return ReturnContent.error(ErrorCode.SYSTEM_ERROR,"删除数据库字段失败");
        }
        return ReturnContent.success();
    }
}
