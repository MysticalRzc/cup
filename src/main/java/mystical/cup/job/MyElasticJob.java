package mystical.cup.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Rzc on 2018/9/13.
 */
public class MyElasticJob implements SimpleJob{

    public MyElasticJob(){
        logger.info("MyElasticJob is building");
    }

    Logger logger = LoggerFactory.getLogger(MyElasticJob.class);

    @Override
    public void execute(ShardingContext shardingContext){
        logger.debug("MyElasticJob is running ");
        switch(shardingContext.getShardingItem()){
            case 0:
                logger.debug("MyElasticJob case 0 is running ");
                break;

        }
    }
}
