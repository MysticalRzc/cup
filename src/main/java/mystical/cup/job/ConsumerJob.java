package mystical.cup.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import mystical.cup.config.ProjectConfig;
import mystical.cup.dao.mapper.HttpProducerDataMapper;
import mystical.cup.model.CatchModel;
import mystical.cup.model.database.HttpProducerDataMode;
import mystical.cup.utils.ConvertUtil;
import mystical.cup.utils.RedisUtil;
import mystical.cup.utils.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Rzc on 2019/1/22.
 */
public class ConsumerJob implements SimpleJob{

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private HttpProducerDataMapper httpProducerDataMapper;


    @Override
    public void execute(ShardingContext shardingContext){
        String redisResp = redisUtil.queuePop(ProjectConfig.REDIS_CONTROLLER_CATCH_LIST_POST);
        CatchModel catchModel = VerifyUtil.isBlank(redisResp) ? null: ConvertUtil.fromJson(CatchModel.class,redisResp);
        if(catchModel!=null){
            HttpProducerDataMode httpProducerDataMode = new HttpProducerDataMode();
            catchModel.copyToMode(httpProducerDataMode);
            httpProducerDataMapper.insert(httpProducerDataMode);
        }
    }
}
