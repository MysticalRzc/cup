//package mystical.cup.job;
//import com.dangdang.ddframe.job.api.ShardingContext;
//import com.dangdang.ddframe.job.api.simple.SimpleJob;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * Created by Rzc on 2018/9/14.
// */
//
//public class StockSimpleJob implements SimpleJob {
//
//    @Override
//    public void execute(ShardingContext shardingContext) {
//        System.out.println(String.format("------Thread ID: %s, 任务总片数: %s, " +
//                        "当前分片项: %s.当前参数: %s,"+
//                        "当前任务名称: %s.当前任务参数: %s"
//                ,
//                Thread.currentThread().getId(),
//                shardingContext.getShardingTotalCount(),
//                shardingContext.getShardingItem(),
//                shardingContext.getShardingParameter(),
//                shardingContext.getJobName(),
//                shardingContext.getJobParameter()
//
//        ));
//
//    }
//}