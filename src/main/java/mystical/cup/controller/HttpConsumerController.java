package mystical.cup.controller;

import lombok.extern.slf4j.Slf4j;
import mystical.cup.handler.AccessCheck;
import mystical.cup.service.HttpConsumerService;
import mystical.cup.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Rzc on 2018/8/13.
 */
@Slf4j
@RestController
public class HttpConsumerController{
    @Autowired
    private HttpConsumerService httpConsumerService;

    @AccessCheck(requestUrl = "/postCatch")
    @RequestMapping(value = {"{a}", "{a}/{b}", "{a}/{b}/{c}"
            , "{a}/{b}/{c}/{d}", "{a}/{b}/{c}/{d}/{e}", "{a}/{b}/{c}/{d}/{e}/{f}"})
    public Object httpConsumer(@RequestBody(required = false) Map reqMap) throws UnsupportedEncodingException{
        return httpConsumerService.httpConsumerService(reqMap);
    }
}