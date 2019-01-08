package mystical.cup.service;

import mystical.cup.dao.mapper.HttpConsumerMapper;
import mystical.cup.model.ReturnContent;
import mystical.cup.model.database.HttpConsumerMode;
import mystical.cup.model.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Rzc on 2018/11/2.
 */
@Service
public class ConsumerConfigService{
    @Autowired
    private HttpConsumerMapper httpConsumerMapper;

    public ReturnContent updateController(HttpConsumerMode httpConsumerMode){

        if(httpConsumerMapper.updateByPrimaryKeySelective(httpConsumerMode) < 0){
            return ReturnContent.error(ErrorCode.SYSTEM_ERROR,"更新数据库字段失败");
        }
        return ReturnContent.success();
    }

    public ReturnContent addController(HttpConsumerMode httpConsumerMode){

        if(httpConsumerMapper.insertSelective(httpConsumerMode)< 0){
            return ReturnContent.error(ErrorCode.SYSTEM_ERROR,"添加数据库字段失败");
        }
        return ReturnContent.success();
    }
    public ReturnContent deleteController(HttpConsumerMode httpConsumerMode){

        if(httpConsumerMapper.deleteByPrimaryKey(httpConsumerMode.getId())< 0){
            return ReturnContent.error(ErrorCode.SYSTEM_ERROR,"删除数据库字段失败");
        }
        return ReturnContent.success();
    }
}
