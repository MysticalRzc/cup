package mystical.cup.model.vo;

import lombok.Data;

/**
 * Created by Rzc on 2018/8/16.
 */
@Data
public class ThreadMode{
    private String groupGid;
    private String threadGid;
    private Object data;
    private boolean success;

    public ThreadMode retSuccess(Object data){
        this.setData(data);
        this.setSuccess(true);
        return this;
    }
    public ThreadMode retSuccess(){
        this.setSuccess(true);
        return this;
    }

    public static ThreadMode success(String groupGid, String threadGid, Object data){
        ThreadMode threadMode = new ThreadMode();
        ThreadMode.setGid(threadMode,groupGid,threadGid);
        threadMode.setData(data);
        return threadMode;
    }
    public static ThreadMode exception(String groupGid, String threadGid, Exception exception){
        ThreadMode threadMode = new ThreadMode();
        ThreadMode.setGid(threadMode,groupGid,threadGid);
        threadMode.setData(exception);
        threadMode.setSuccess(false);
        return threadMode;
    }
    private static void setGid(ThreadMode threadMode, String groupGid, String threadGid){
        threadMode.setGroupGid(groupGid);
        threadMode.setThreadGid(threadGid);
    }
}
