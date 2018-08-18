package mystical.cup.service;

import mystical.cup.model.ReturnContent;
import mystical.cup.utils.RandomUtil;
import org.springframework.stereotype.Service;

/**
 * Created by Rzc on 2018/11/2.
 */
@Service
public class UserTockenService{
    public boolean checkUserTocken(String user,String tocken){
        return true;
    }
    public ReturnContent setUserTocken(String user){
        return ReturnContent.success(RandomUtil.randomGid());
    }
}
