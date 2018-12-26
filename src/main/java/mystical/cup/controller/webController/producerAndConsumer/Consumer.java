package mystical.cup.controller.webController.producerAndConsumer;

import com.google.gson.Gson;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.extern.slf4j.Slf4j;
import mystical.cup.dao.mapper.ConfigControllerMapper;
import mystical.cup.dao.mapper.HttpCatchMapper;
import mystical.cup.model.ReturnContent;
import mystical.cup.model.database.*;
import mystical.cup.model.enums.ErrorCode;
import mystical.cup.model.vo.requestVO.ControllerConfigReq;
import mystical.cup.model.vo.requestVO.ControllerSearchReq;
import mystical.cup.service.ControllerConfigService;
import mystical.cup.service.UserTockenService;
import mystical.cup.utils.ConvertUtil;
import mystical.cup.utils.VerifyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mystical on 2018/8/10.
 */
@Slf4j
@RestController
public class Consumer{

    @Autowired
    private ConfigControllerMapper configControllerMapper;
    @Autowired
    private HttpCatchMapper catchMapper;
    @Autowired
    private UserTockenService userTockenService;
    @Autowired
    private ControllerConfigService controllerConfigService;

    @PostMapping("/httpCatch/list")
    public String httpCatchList(int page, int limit){
        HttpCatchExample example = new HttpCatchExample( );
        List<HttpCatch> httpCatches = catchMapper.selectByExample(example);
        return new Gson( ).toJson(httpCatches);
    }

    @PostMapping("/controller/list")
    public ReturnContent listController(@Param("page") int page, @Param("limit") int limit){
        log.debug("ControllerList has invoke");
        ConfigControllerExample example = new ConfigControllerExample( );
        List<ConfigController> configList = configControllerMapper.selectLimit((page - 1) * limit, limit);
        Map resultSearch = new HashMap( );
        resultSearch.put("count", configControllerMapper.countByExample(example));
        resultSearch.put("data", configList);
        log.debug("ControllerList response={}", ConvertUtil.toJson(resultSearch));
        return ReturnContent.success(resultSearch);
    }

    @PostMapping("/controller/search")
    public ReturnContent searchController(@RequestBody ControllerSearchReq controllerSearchReq){
        log.debug("Controller search has invoke req={}", new Gson( ).toJson(controllerSearchReq));
        ConfigControllerExample example = new ConfigControllerExample( );
        Date beginTime = ConvertUtil.stampToDate(controllerSearchReq.getBeginTime( ), "yyyy-MM-dd HH");
        Date endTime = ConvertUtil.stampToDate(controllerSearchReq.getEndTime( ), "yyyy-MM-dd HH");

        ConfigControllerExample.Criteria criteria = example.createCriteria( );

        if(!VerifyUtil.isBlank(controllerSearchReq.getControllerUrl( ))){
            criteria.andControllerUrlLike("%"+controllerSearchReq.getControllerUrl( )+"%");
        }
        if(!VerifyUtil.isBlank(controllerSearchReq.getCreator( ))){
            criteria.andCreatorEqualTo(controllerSearchReq.getCreator( ));
        }
        if(beginTime != null && endTime != null){
            criteria.andCreateTimeBetween(beginTime, endTime);
        }else if(beginTime == null && endTime != null){
            criteria.andCreateTimeLessThanOrEqualTo(endTime);
        }else if(beginTime != null && endTime == null){
            criteria.andCreateTimeGreaterThanOrEqualTo(beginTime);
        }
        List<ConfigController> configList = configControllerMapper.selectByExample(example);
        Map resultSearch = new HashMap( );
        resultSearch.put("count", configControllerMapper.countByExample(example));
        resultSearch.put("data", configList);
        log.debug("ControllerList response={}", ConvertUtil.toJson(resultSearch));
        return ReturnContent.success(resultSearch);
    }


    @PostMapping("/controller/update")
    public ReturnContent controllerUpdate(@RequestBody ControllerConfigReq controllerConfigReq){
        log.info("update controller invoke req={}", ConvertUtil.toJson(controllerConfigReq));

        if(!controllerConfigReq.checkRequestParam_update( ).isSuccess( )){
            return controllerConfigReq.checkRequestParam_update( );
        }

        if(!userTockenService.checkUserTocken(controllerConfigReq.getCreator( ), controllerConfigReq.getTocken( ))){
            return ReturnContent.error(ErrorCode.TOCKEN_INVALID);
        }

        ConfigController configController = new ConfigController( );
        configController.setId(controllerConfigReq.getId( ));
        configController.setControllerUrl(controllerConfigReq.getRequestUrl( ));
        configController.setInputParam(controllerConfigReq.getRequestParam( ));
        configController.setOutputParam(controllerConfigReq.getResponseParam( ));
        configController.setAk(controllerConfigReq.getAk( ));
        configController.setSk(controllerConfigReq.getSk( ));
        configController.setDecription(controllerConfigReq.getDesc( ));
        configController.setType(controllerConfigReq.getRequestType( ));

        return controllerConfigService.updateController(configController);
    }

    @PostMapping("/controller/add")
    public ReturnContent controllerAdd(@RequestBody ControllerConfigReq controllerConfigReq){
        log.info("add controller invoke req={}", ConvertUtil.toJson(controllerConfigReq));

        if(!controllerConfigReq.checkRequestParam_Add( ).isSuccess( )){
            return controllerConfigReq.checkRequestParam_Add( );
        }

        if(!userTockenService.checkUserTocken(controllerConfigReq.getCreator( ), controllerConfigReq.getTocken( ))){
            return ReturnContent.error(ErrorCode.TOCKEN_INVALID);
        }

        ConfigController configController = new ConfigController( );
        configController.setControllerUrl(controllerConfigReq.getRequestUrl( ));
        configController.setInputParam(controllerConfigReq.getRequestParam( ));
        configController.setOutputParam(controllerConfigReq.getResponseParam( ));
        configController.setAk(controllerConfigReq.getAk( ));
        configController.setSk(controllerConfigReq.getSk( ));
        configController.setDecription(controllerConfigReq.getDesc( ));
        configController.setType(controllerConfigReq.getRequestType( ));
        configController.setCreator(controllerConfigReq.getCreator( ));
        configController.setIsValid(1);

        return controllerConfigService.addController(configController);
    }

    @PostMapping("/controller/delete")
    public ReturnContent controllerDelete(@RequestBody ControllerConfigReq controllerConfigReq){
        log.info("delete controller invoke req={}", ConvertUtil.toJson(controllerConfigReq));

        if(!controllerConfigReq.checkRequestParam_Delete( ).isSuccess( )){
            return controllerConfigReq.checkRequestParam_Delete( );
        }

        if(!userTockenService.checkUserTocken(controllerConfigReq.getCreator( ), controllerConfigReq.getTocken( ))){
            return ReturnContent.error(ErrorCode.TOCKEN_INVALID);
        }

        ConfigController configController = new ConfigController( );
        configController.setId(controllerConfigReq.getId( ));

        return controllerConfigService.deleteController(configController);
    }
}