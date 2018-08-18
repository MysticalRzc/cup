package mystical.cup.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import mystical.cup.dao.mapper.ConfigControllerMapper;
import mystical.cup.model.ReturnContent;
import mystical.cup.model.database.ConfigController;
import mystical.cup.model.database.ConfigControllerExample;
import mystical.cup.model.enums.ErrorCode;
import mystical.cup.utils.AkSkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by Rzc on 2018/10/3.
 */
@Slf4j
@Service
public class RequestHandlerService{

    @Autowired
    private ConfigControllerMapper configControllerMapper;

    public Object handlerService(Map reqMap) throws UnsupportedEncodingException{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes( )).getRequest( );
        request.setCharacterEncoding("UTF-8");
        String requestURI = removeCatch(request.getServletPath( ));
        log.info("Catch request url={} request={}", requestURI, new Gson( ).toJson(reqMap),request.getMethod());
        try{
            ConfigControllerExample configControllerExample = new ConfigControllerExample();
            configControllerExample.createCriteria().andControllerUrlEqualTo(requestURI).andTypeEqualTo(request.getMethod());
            List<ConfigController> configControllerList = configControllerMapper.selectByExample(configControllerExample);

            for(ConfigController ctl : configControllerList){
                if(AkSkUtil.checkAkSk(null)){
                    log.info("Catch Post response url ={} result={} ",requestURI,ctl.getOutputParam());
                    return ctl.getOutputParam();
                }else{
                    log.warn("check AkSk failed response url ={}",requestURI);
                }
            }
        }catch(Exception ex){
            log.error("postCatch Error ex,",ex);
        }
        return ReturnContent.error(ErrorCode.CONTRLLER_NOFOUND);
    }

    private String removeCatch(String requestUrl){
        int index = requestUrl.indexOf('/',1);
        return requestUrl.substring(index);
    }
}
