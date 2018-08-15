package mystical.cup.model.thread;

        import mystical.cup.model.vo.ThreadMode;
        import mystical.cup.utils.RandomUtil;

        import java.util.concurrent.Callable;

/**
 * Created by Rzc on 2018/8/16.
 */
public abstract class CallableAbstract implements Callable<ThreadMode>{

    final ThreadMode threadMode = new ThreadMode();

    public CallableAbstract(String groupGid, String threadGid){
        this.threadMode.setGroupGid(groupGid);
        this.threadMode.setThreadGid(threadGid);
    }
    public CallableAbstract(String groupGid, String threadGid, Object data){
        this.threadMode.setGroupGid(groupGid);
        this.threadMode.setThreadGid(threadGid);
        this.threadMode.setData(data);
    }
    public String getThreadGid(){
        return threadMode.getThreadGid();
    }
    public String getGroupGid(){
        return threadMode.getGroupGid();
    }
}
