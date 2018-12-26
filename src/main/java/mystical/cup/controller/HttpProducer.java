package mystical.cup.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rzc on 2018/12/26.
 */
@RestController("/httpProducer")
public class HttpProducer{

    @GetMapping("/post")
    public void httpPost(){

    }
}
