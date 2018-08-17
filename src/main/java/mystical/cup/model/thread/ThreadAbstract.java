package mystical.cup.model.thread;

import mystical.cup.model.vo.ThreadMode;

/**
 * Created by Rzc on 2018/8/17.
 */
public abstract class ThreadAbstract extends Thread{
    final ThreadMode threadMode = new ThreadMode( );

    public ThreadAbstract(String groupGid, String threadGid){
        this.threadMode.setGroupGid(groupGid);
        this.threadMode.setThreadGid(threadGid);
    }

    public ThreadAbstract(String groupGid, String threadGid, Object data){
        this.threadMode.setGroupGid(groupGid);
        this.threadMode.setThreadGid(threadGid);
        this.threadMode.setData(data);
    }

    public String getThreadGid(){
        return threadMode.getThreadGid( );
    }

    public String getGroupGid(){
        return threadMode.getGroupGid( );
    }
}
