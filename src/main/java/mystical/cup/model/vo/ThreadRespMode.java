package mystical.cup.model.vo;

import lombok.Data;

/**
 * Created by Rzc on 2018/8/16.
 */
@Data
public class ThreadRespMode{
    private String groupGid;
    private String threadGid;
    private Object data;
    private Exception exception;

    public static ThreadRespMode success(String groupGid,String threadGid,Object data){
        ThreadRespMode threadRespMode = new ThreadRespMode();
        ThreadRespMode.setGid(threadRespMode,groupGid,threadGid);
        threadRespMode.setData(data);
        return  threadRespMode;
    }
    public static ThreadRespMode exception(String groupGid,String threadGid,Exception exception){
        ThreadRespMode threadRespMode = new ThreadRespMode();
        ThreadRespMode.setGid(threadRespMode,groupGid,threadGid);
        threadRespMode.setException(exception);
        return  threadRespMode;
    }
    private static void setGid(ThreadRespMode threadRespMode,String groupGid,String threadGid){
        threadRespMode.setGroupGid(groupGid);
        threadRespMode.setThreadGid(threadGid);
    }
}
