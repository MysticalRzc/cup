package mystical.cup.model.thread;

import mystical.cup.model.vo.ThreadMode;

/**
 * Created by Rzc on 2018/8/17.
        */
public class CallableDemo extends CallableAbstract{

    public CallableDemo(String groupGid, String threadGid){
        super(groupGid, threadGid);
    }

    @Override
    public ThreadMode call() throws Exception{
        System.out.println("线程" + getThreadGid() + "已经启动" );
        Thread.sleep(30000);
        System.out.println("线程" + getThreadGid() + "已经完成" );
        return threadMode.retSuccess();
    }
}
