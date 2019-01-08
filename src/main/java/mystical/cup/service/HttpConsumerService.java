package mystical.cup.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import mystical.cup.dao.mapper.HttpConsumerMapper;
import mystical.cup.model.ReturnContent;
import mystical.cup.model.database.HttpConsumerMode;
import mystical.cup.model.database.HttpConsumerExample;
import mystical.cup.model.enums.ErrorCode;
import mystical.cup.utils.akskUtil.AkSkUtil;
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
public class HttpConsumerService{

    @Autowired
    private HttpConsumerMapper httpConsumerMapper;

    public Object httpConsumerService(Map reqMap) throws UnsupportedEncodingException{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes( )).getRequest( );
        request.setCharacterEncoding("UTF-8");
        String requestURI = request.getServletPath( );
        log.debug("Catch request url={} request={}", requestURI, new Gson( ).toJson(reqMap),request.getMethod());
        try{
            HttpConsumerExample httpConsumerExample = new HttpConsumerExample();
            httpConsumerExample.createCriteria().andControllerUrlEqualTo(requestURI).andTypeEqualTo(request.getMethod());
            List<HttpConsumerMode> httpConsumerModeList = httpConsumerMapper.selectByExample(httpConsumerExample);

            for(HttpConsumerMode httpConsumerMode : httpConsumerModeList){
                if(AkSkUtil.checkAkSk(httpConsumerMode,"weshare")){
                    log.info("httpConsumer response url ={} result={} ",requestURI, httpConsumerMode.getOutputParam());
                    return httpConsumerMode.getOutputParam();
                }else{
                    return ReturnContent.error(ErrorCode.TOCKEN_CHECK_FAILED);
                }
            }
        }catch(Exception ex){
            log.error("postCatch Error ex,",ex);
        }
        return ReturnContent.error(ErrorCode.CONTRLLER_NOFOUND);
    }
}
