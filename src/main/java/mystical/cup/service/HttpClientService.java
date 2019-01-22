package mystical.cup.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import mystical.cup.model.database.HttpProducerMode;
import mystical.cup.utils.ConvertUtil;
import mystical.cup.utils.Utility;
import mystical.cup.utils.akskUtil.WeshareTokenHelper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Rzc on 2018/12/26.
 */
@Slf4j
@Service
public class HttpClientService{

    RestTemplate restTemplate = new RestTemplate( );
    public final static String WESHAREAUTH_TOKEN = "X-WeshareAuth-AccessCheck";

    public <T> T wesharePost(String baseUrl, String url, Object body, Class<T> tClass, HttpProducerMode httpProducerMode){
        log.debug("weshare post url = {}, body = {}", url, ConvertUtil.toJson(body));
        HttpHeaders headers = new HttpHeaders( );
        WeshareTokenHelper weshareTokenHelper = new WeshareTokenHelper(httpProducerMode.getAk( ), httpProducerMode.getSk( ));
        int expireTime = Utility.getTimeStep_S( ) + (30 * 60);

        String queryParam = null;
        if(url.contains("?")){
            String[] split = url.split("[?]");
            url = split[0];
            queryParam = split[1];
        }
        headers.set(WESHAREAUTH_TOKEN, weshareTokenHelper.generateToken(url, "POST", queryParam, new Gson( ).toJson(body), expireTime));
        headers.set("Content-type", "application/json;charset=utf-8");
        HttpEntity<String> entity = new HttpEntity<String>(new Gson( ).toJson(body), headers);
        return restTemplate.postForObject(baseUrl + url, entity, tClass);
    }

    public <T> T post(HttpProducerMode httpProducerMode, Object body, Class<T> tClass){
        log.debug("vendor post url = {}", httpProducerMode.getReqUrl( ));
        HttpHeaders headers = new HttpHeaders( );
        HttpEntity<String> entity = new HttpEntity<String>(new Gson( ).toJson(body), headers);
        return restTemplate.postForObject(httpProducerMode.getReqUrl( ), entity, tClass);
    }

    public <T> T post(String url, Object body, Class<T> tClass){
        log.debug("vendor post url = {}", url);
        HttpHeaders headers = new HttpHeaders( );
        HttpEntity<String> entity = new HttpEntity<String>(new Gson( ).toJson(body), headers);
        return restTemplate.postForObject(url, entity, tClass);
    }
}
