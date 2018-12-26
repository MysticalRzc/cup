package mystical.cup.controller.webController.producerAndConsumer;

import com.google.gson.Gson;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.extern.slf4j.Slf4j;
import mystical.cup.dao.mapper.HttpConsumerDateMapper;
import mystical.cup.dao.mapper.HttpConsumerMapper;
import mystical.cup.model.ReturnContent;
import mystical.cup.model.database.*;
import mystical.cup.model.enums.ErrorCode;
import mystical.cup.model.vo.requestVO.ConsumerSearchReq;
import mystical.cup.model.vo.requestVO.ConsumerConfigReq;
import mystical.cup.service.ConsumerConfigService;
import mystical.cup.service.UserTockenService;
import mystical.cup.utils.ConvertUtil;
import mystical.cup.utils.VerifyUtil;
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
public class ConsumerWebController{

    @Autowired
    private HttpConsumerMapper httpConsumerMapper;
    @Autowired
    private HttpConsumerDateMapper httpConsumerDateMapper;
    @Autowired
    private UserTockenService userTockenService;
    @Autowired
    private ConsumerConfigService consumerConfigService;

    @PostMapping("/httpCatch/list")
    public String httpCatchList(int page, int limit){
        HttpConsumerDateExample example = new HttpConsumerDateExample( );
        List<HttpConsumerDateMode> httpConsumerDateModeList = httpConsumerDateMapper.selectByExample(example);
        return new Gson( ).toJson(httpConsumerDateModeList);
    }

    @PostMapping("/controller/list")
    public ReturnContent listController(@Param("page") int page, @Param("limit") int limit){
        log.debug("ControllerList has invoke");
        HttpConsumerExample example = new HttpConsumerExample( );
        List<HttpConsumerMode> configList = httpConsumerMapper.selectLimit((page - 1) * limit, limit);
        Map resultSearch = new HashMap( );
        resultSearch.put("count", httpConsumerMapper.countByExample(example));
        resultSearch.put("data", configList);
        log.debug("ControllerList response={}", ConvertUtil.toJson(resultSearch));
        return ReturnContent.success(resultSearch);
    }

    @PostMapping("/controller/search")
    public ReturnContent searchController(@RequestBody ConsumerSearchReq consumerSearchReq){
        log.debug("Controller search has invoke req={}", new Gson( ).toJson(consumerSearchReq));
        HttpConsumerExample example = new HttpConsumerExample( );
        Date beginTime = ConvertUtil.stampToDate(consumerSearchReq.getBeginTime( ), "yyyy-MM-dd HH");
        Date endTime = ConvertUtil.stampToDate(consumerSearchReq.getEndTime( ), "yyyy-MM-dd HH");

        HttpConsumerExample.Criteria criteria = example.createCriteria( );

        if(!VerifyUtil.isBlank(consumerSearchReq.getControllerUrl( ))){
            criteria.andControllerUrlLike("%"+ consumerSearchReq.getControllerUrl( )+"%");
        }
        if(!VerifyUtil.isBlank(consumerSearchReq.getCreator( ))){
            criteria.andCreatorEqualTo(consumerSearchReq.getCreator( ));
        }
        if(beginTime != null && endTime != null){
            criteria.andCreateTimeBetween(beginTime, endTime);
        }else if(beginTime == null && endTime != null){
            criteria.andCreateTimeLessThanOrEqualTo(endTime);
        }else if(beginTime != null && endTime == null){
            criteria.andCreateTimeGreaterThanOrEqualTo(beginTime);
        }
        List<HttpConsumerMode> configList = httpConsumerMapper.selectByExample(example);
        Map resultSearch = new HashMap( );
        resultSearch.put("count", httpConsumerMapper.countByExample(example));
        resultSearch.put("data", configList);
        log.debug("ControllerList response={}", ConvertUtil.toJson(resultSearch));
        return ReturnContent.success(resultSearch);
    }


    @PostMapping("/controller/update")
    public ReturnContent controllerUpdate(@RequestBody ConsumerConfigReq consumerConfigReq){
        log.info("update controller invoke req={}", ConvertUtil.toJson(consumerConfigReq));

        if(!consumerConfigReq.checkRequestParam_update( ).isSuccess( )){
            return consumerConfigReq.checkRequestParam_update( );
        }

        if(!userTockenService.checkUserTocken(consumerConfigReq.getCreator( ), consumerConfigReq.getTocken( ))){
            return ReturnContent.error(ErrorCode.TOCKEN_INVALID);
        }

        HttpConsumerMode httpConsumerMode = new HttpConsumerMode( );
        httpConsumerMode.setId(consumerConfigReq.getId( ));
        httpConsumerMode.setControllerUrl(consumerConfigReq.getRequestUrl( ));
        httpConsumerMode.setInputParam(consumerConfigReq.getRequestParam( ));
        httpConsumerMode.setOutputParam(consumerConfigReq.getResponseParam( ));
        httpConsumerMode.setAk(consumerConfigReq.getAk( ));
        httpConsumerMode.setSk(consumerConfigReq.getSk( ));
        httpConsumerMode.setDecription(consumerConfigReq.getDesc( ));
        httpConsumerMode.setType(consumerConfigReq.getRequestType( ));

        return consumerConfigService.updateController(httpConsumerMode);
    }

    @PostMapping("/controller/add")
    public ReturnContent controllerAdd(@RequestBody ConsumerConfigReq consumerConfigReq){
        log.info("add controller invoke req={}", ConvertUtil.toJson(consumerConfigReq));

        if(!consumerConfigReq.checkRequestParam_Add( ).isSuccess( )){
            return consumerConfigReq.checkRequestParam_Add( );
        }

        if(!userTockenService.checkUserTocken(consumerConfigReq.getCreator( ), consumerConfigReq.getTocken( ))){
            return ReturnContent.error(ErrorCode.TOCKEN_INVALID);
        }

        HttpConsumerMode httpConsumerMode = new HttpConsumerMode( );
        httpConsumerMode.setControllerUrl(consumerConfigReq.getRequestUrl( ));
        httpConsumerMode.setInputParam(consumerConfigReq.getRequestParam( ));
        httpConsumerMode.setOutputParam(consumerConfigReq.getResponseParam( ));
        httpConsumerMode.setAk(consumerConfigReq.getAk( ));
        httpConsumerMode.setSk(consumerConfigReq.getSk( ));
        httpConsumerMode.setDecription(consumerConfigReq.getDesc( ));
        httpConsumerMode.setType(consumerConfigReq.getRequestType( ));
        httpConsumerMode.setCreator(consumerConfigReq.getCreator( ));
        httpConsumerMode.setIsValid(1);

        return consumerConfigService.addController(httpConsumerMode);
    }

    @PostMapping("/controller/delete")
    public ReturnContent controllerDelete(@RequestBody ConsumerConfigReq consumerConfigReq){
        log.info("delete controller invoke req={}", ConvertUtil.toJson(consumerConfigReq));

        if(!consumerConfigReq.checkRequestParam_Delete( ).isSuccess( )){
            return consumerConfigReq.checkRequestParam_Delete( );
        }

        if(!userTockenService.checkUserTocken(consumerConfigReq.getCreator( ), consumerConfigReq.getTocken( ))){
            return ReturnContent.error(ErrorCode.TOCKEN_INVALID);
        }

        HttpConsumerMode httpConsumerMode = new HttpConsumerMode( );
        httpConsumerMode.setId(consumerConfigReq.getId( ));

        return consumerConfigService.deleteController(httpConsumerMode);
    }
}