package mystical.cup.controller.webController.producerAndConsumer;
import com.google.gson.Gson;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.extern.slf4j.Slf4j;
import mystical.cup.dao.mapper.HttpProducerDataMapper;
import mystical.cup.dao.mapper.HttpProducerMapper;
import mystical.cup.model.ReturnContent;
import mystical.cup.model.database.*;
import mystical.cup.model.enums.ErrorCode;
import mystical.cup.model.vo.requestVO.ConsumerConfigReq;
import mystical.cup.model.vo.requestVO.ConsumerSearchReq;
import mystical.cup.model.vo.requestVO.ProducorConfigReq;
import mystical.cup.service.ConsumerConfigService;
import mystical.cup.service.ProductorConfigService;
import mystical.cup.service.UserTockenService;
import mystical.cup.utils.ConvertUtil;
import mystical.cup.utils.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

import java.util.*;

/**
 * Created by Rzc on 2018/12/11.
 */
@Slf4j
@RequestMapping("/httpProducor")
@RestController
public class ProducerWebController{
    @Autowired
    private HttpProducerMapper httpProducerMapper;
    @Autowired
    private HttpProducerDataMapper httpProducerDataMapper;
    @Autowired
    private UserTockenService userTockenService;
    @Autowired
    private ProductorConfigService productorConfigService;

    @PostMapping("/data/list")
    public String httpProducorDataList(int page, int limit){
        HttpProducerDataExample example = new HttpProducerDataExample( );
        List<HttpProducerDataMode> httpProducerDataModeList = httpProducerDataMapper.selectByExample(example);
        return new Gson( ).toJson(httpProducerDataModeList);
    }

    @PostMapping("/config/list")
    public ReturnContent listController(@Param("page") int page, @Param("limit") int limit){
        log.debug("producer config list has invoke");
        HttpProducerExample example = new HttpProducerExample( );
        List<HttpProducerMode> httpProducerModeList = httpProducerMapper.selectLimit((page - 1) * limit, limit);
        Map resultSearch = new HashMap( );
        resultSearch.put("count", httpProducerMapper.countByExample(example));
        resultSearch.put("data", httpProducerModeList);
        log.debug("HttpProducor config list  response={}", ConvertUtil.toJson(resultSearch));
        return ReturnContent.success(resultSearch);
    }

    @PostMapping("/config/search")
    public ReturnContent searchController(@RequestBody ProducorConfigReq producorConfigReq){
        log.debug("producer config search has invoke req={}", new Gson( ).toJson(producorConfigReq));
        HttpProducerExample example = new HttpProducerExample( );
        Date beginTime = ConvertUtil.stampToDate(producorConfigReq.getBeginTime( ), "yyyy-MM-dd HH");
        Date endTime = ConvertUtil.stampToDate(producorConfigReq.getEndTime( ), "yyyy-MM-dd HH");

        HttpProducerExample.Criteria criteria = example.createCriteria( );

        if(!VerifyUtil.isBlank(producorConfigReq.getRequestUrl( ))){
            criteria.andReqUrlLike("%"+ producorConfigReq.getRequestUrl( )+"%");
        }
//        if(!VerifyUtil.isBlank(consumerSearchReq.getCreator( ))){
//            criteria.andCreatorEqualTo(consumerSearchReq.getCreator( ));
//        }
        if(beginTime != null && endTime != null){
            criteria.andCreateTimeBetween(beginTime, endTime);
        }else if(beginTime == null && endTime != null){
            criteria.andCreateTimeLessThanOrEqualTo(endTime);
        }else if(beginTime != null && endTime == null){
            criteria.andCreateTimeGreaterThanOrEqualTo(beginTime);
        }
        List<HttpProducerMode> configList = httpProducerMapper.selectByExample(example);
        Map resultSearch = new HashMap( );
        resultSearch.put("count", httpProducerMapper.countByExample(example));
        resultSearch.put("data", configList);
        log.debug("ControllerList response={}", ConvertUtil.toJson(resultSearch));
        return ReturnContent.success(resultSearch);
    }


    @PostMapping("/config/update")
    public ReturnContent controllerUpdate(@RequestBody ProducorConfigReq producorConfigReq){
        log.info("producer config update has invoke req={}", ConvertUtil.toJson(producorConfigReq));

        if(!producorConfigReq.checkRequestParam_update( ).isSuccess( )){
            return producorConfigReq.checkRequestParam_update( );
        }

        if(!userTockenService.checkUserTocken(producorConfigReq.getCreator( ), producorConfigReq.getTocken( ))){
            return ReturnContent.error(ErrorCode.TOCKEN_INVALID);
        }

        HttpProducerMode httpProducerMode = new HttpProducerMode( );
        httpProducerMode.setId(producorConfigReq.getId( ));
        httpProducerMode.setReqUrl(producorConfigReq.getRequestUrl( ));
        httpProducerMode.setReqParaConfig(producorConfigReq.getRequestParamConfig( ));
        httpProducerMode.setReqSuccessCode(producorConfigReq.getRequestSuccessCode( ));

        httpProducerMode.setReqType(producorConfigReq.getRequestType( ));
        httpProducerMode.setReqBeginTime(producorConfigReq.getBeginTime( ));
        httpProducerMode.setReqEndTime(producorConfigReq.getEndTime( ));
        httpProducerMode.setReqStepTime(producorConfigReq.getReqStepTime( ));
        httpProducerMode.setStatistics(producorConfigReq.isStatistic());
        httpProducerMode.setAk(producorConfigReq.getAk( ));
        httpProducerMode.setSk(producorConfigReq.getSk( ));
        httpProducerMode.setAkskCheck(producorConfigReq.isAkskCheck());

        return productorConfigService.updateController(httpProducerMode);
    }

    @PostMapping("/config/add")
    public ReturnContent controllerAdd(@RequestBody ProducorConfigReq producorConfigReq){
        log.info("producer config add has invoke req={}", ConvertUtil.toJson(producorConfigReq));

        if(!producorConfigReq.checkRequestParam_Add( ).isSuccess( )){
            return producorConfigReq.checkRequestParam_Add( );
        }

        if(!userTockenService.checkUserTocken(producorConfigReq.getCreator( ), producorConfigReq.getTocken( ))){
            return ReturnContent.error(ErrorCode.TOCKEN_INVALID);
        }

        HttpProducerMode httpProducerMode = new HttpProducerMode( );
        httpProducerMode.setReqUrl(producorConfigReq.getRequestUrl( ));
        httpProducerMode.setReqParaConfig(producorConfigReq.getRequestParamConfig( ));
        httpProducerMode.setReqSuccessCode(producorConfigReq.getRequestSuccessCode( ));
        httpProducerMode.setReqType(producorConfigReq.getRequestType( ));
        if(!VerifyUtil.isBlank(producorConfigReq.getBeginTime()) && !VerifyUtil.isBlank(producorConfigReq.getEndTime())){
            httpProducerMode.setReqBeginTime(ConvertUtil.stampToDate(producorConfigReq.getBeginTime( ), "yyyy-MM-dd HH-mm").getTime( ) + "");
            httpProducerMode.setReqEndTime(ConvertUtil.stampToDate(producorConfigReq.getEndTime( ), "yyyy-MM-dd HH-mm").getTime( ) + "");
        }
        httpProducerMode.setReqStepTime(producorConfigReq.getReqStepTime( ));
        httpProducerMode.setStatistics(producorConfigReq.isStatistic());
        httpProducerMode.setAk(producorConfigReq.getAk( ));
        httpProducerMode.setSk(producorConfigReq.getSk( ));
        httpProducerMode.setAkskCheck(producorConfigReq.isAkskCheck());
        httpProducerMode.setCreator(producorConfigReq.getCreator());

        return productorConfigService.addController(httpProducerMode);
    }

    @PostMapping("/config/delete")
    public ReturnContent controllerDelete(@RequestBody ProducorConfigReq producorConfigReq){
        log.info("delete producer invoke req={}", ConvertUtil.toJson(producorConfigReq));

        if(!producorConfigReq.checkRequestParam_Delete( ).isSuccess( )){
            return producorConfigReq.checkRequestParam_Delete( );
        }

        if(!userTockenService.checkUserTocken(producorConfigReq.getCreator( ), producorConfigReq.getTocken( ))){
            return ReturnContent.error(ErrorCode.TOCKEN_INVALID);
        }

        HttpProducerMode httpProducerMode = new HttpProducerMode( );
        httpProducerMode.setId(producorConfigReq.getId());

        return productorConfigService.deleteController(httpProducerMode);
    }
}