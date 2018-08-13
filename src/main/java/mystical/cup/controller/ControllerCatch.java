package mystical.cup.controller;

import com.google.gson.Gson;
import mystical.cup.model.database.ReturnContent;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Rzc on 2018/8/13.
 */
@RestController
public class ControllerCatch{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger (ControllerCatch.class);

    private Gson gson = new Gson();

    @RequestMapping(value = {"/{a}", "/{a}/{b}", "/{a}/{b}/{c}"
            , "/{a}/{b}/{c}/{d}", "/{a}/{b}/{c}/{d}/{e}", "/{a}/{b}/{c}/{d}/{e}/{f}"}, method = RequestMethod.POST)
    public ReturnContent controllerPostCatch(@RequestBody Map reqMap){
        logger.info("Catch Get request request={}",new Gson().toJson(reqMap));
        return ReturnContent.success();
    }
}

