package mystical.cup.controller;

import com.google.gson.Gson;
import mystical.cup.model.ReturnContent;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by Rzc on 2018/8/13.
 */
@RestController
public class PostCatch{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PostCatch.class);

    private Gson gson = new Gson( );

    int all = 0;

    //    @RequestMapping(value = {"/postCatch/{a}", "/postCatch/{a}/{b}", "/postCatch/{a}/{b}/{c}"
//            , "/postCatch/{a}/{b}/{c}/{d}", "/postCatch/{a}/{b}/{c}/{d}/{e}", "/postCatch/{a}/{b}/{c}/{d}/{e}/{f}"}, method = RequestMethod.POST)
    public ReturnContent controllerPostCatch(@RequestBody Map reqMap) throws UnsupportedEncodingException{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes( )).getRequest( );
        request.setCharacterEncoding("UTF-8");

        String requestURI = request.getServletPath( );
        logger.info("===================Catch Get request request===================");
        logger.info("requestURL:" + requestURI);
        logger.info("Catch Get request request={}", new Gson( ).toJson(reqMap));
        try{
            countData(reqMap);
        }catch(Exception ex){
            System.out.println("countDataError");
        }
        return ReturnContent.success( );
    }

    private void countData(Map reqMap){
        List<Object> list = (List<Object>) reqMap.get("data");
        int count = list.size( );
        all += count;
        System.out.println(String.format("当前上传%d 共上传%d 条", count, all));
    }
}
