package mystical.cup.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mystical on 2018/8/10.
 */
@RestController
public class appver {
    @GetMapping("/appver")
    public String appver(){
        return "welcome to cup project!";
    }
}
