package mystical.cup.controller;

import lombok.extern.slf4j.Slf4j;
import mystical.cup.handler.AccessCheck;
import mystical.cup.service.RequestHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Rzc on 2018/8/13.
 */
@Slf4j
@RestController
public class HttpConsumer{
    @Autowired
    private RequestHandlerService requestHandlerService;

    @AccessCheck(requestUrl = "/postCatch")
    @RequestMapping(value = {"/postCatch/{a}", "/postCatch/{a}/{b}", "/postCatch/{a}/{b}/{c}"
            , "/postCatch/{a}/{b}/{c}/{d}", "/postCatch/{a}/{b}/{c}/{d}/{e}", "/postCatch/{a}/{b}/{c}/{d}/{e}/{f}"})
    public Object controllerPostCatch(@RequestBody(required = false) Map reqMap) throws UnsupportedEncodingException{
        log.info("catch controller has invoke");
        return requestHandlerService.handlerService(reqMap);
    }
}