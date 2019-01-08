package mystical.cup.controller;

import mystical.cup.model.database.HttpProducerMode;
import mystical.cup.service.HttpClientService;
import mystical.cup.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rzc on 2018/12/26.
 */
@RestController
@RequestMapping("/httpProducer")
public class HttpProducer{

    @Autowired
    private HttpClientService httpClientService;

    @GetMapping("/post")
    public Object httpPost(){
        Map requestParam = new HashMap<>();
        requestParam.put("user","zhangsan");
        HttpProducerMode httpProducerMode = new HttpProducerMode();
        httpProducerMode.setAk("aaaaa");
        httpProducerMode.setSk("ccccccc");
        Object response =  httpClientService.wesharePost("http://localhost:10151/cup","/aksk/test",requestParam,Object.class,httpProducerMode);
        return response;
    }
}
