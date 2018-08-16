package mystical.cup.model.thread;

        import mystical.cup.model.vo.ThreadRespMode;
        import mystical.cup.utils.RandomUtil;

        import java.util.concurrent.Callable;

/**
 * Created by Rzc on 2018/8/16.
 */
public class ThreadDemo implements Callable<ThreadRespMode>{
    @Override
    public ThreadRespMode call() throws Exception{
        return ThreadRespMode.success(RandomUtil.randomGid(),RandomUtil.randomGid(),"demol");
    }
}
