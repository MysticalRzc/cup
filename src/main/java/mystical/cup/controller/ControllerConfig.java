package mystical.cup.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import mystical.cup.dao.mapper.ConfigControllerMapper;
import mystical.cup.dao.mapper.HttpCatchMapper;
import mystical.cup.model.database.ConfigController;
import mystical.cup.model.database.ConfigControllerExample;
import mystical.cup.model.database.HttpCatch;
import mystical.cup.model.database.HttpCatchExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mystical on 2018/8/10.
 */
@RestController
public class ControllerConfig{

    private static final Logger logger = LoggerFactory.getLogger (ControllerConfig.class);

    @Autowired
    private ConfigControllerMapper configControllerMapper;
    @Autowired
    private HttpCatchMapper catchMapper;

    @GetMapping("/httpCatch/list")
    public String httpCatchList(int page, int limit) {

        HttpCatchExample example = new HttpCatchExample();
        List<HttpCatch> httpCatches = catchMapper.selectByExample(example);
        return new Gson().toJson(httpCatches);
    }

    @GetMapping("/controller/list")
    public Map listController(int page, int limit) {
        ConfigControllerExample example = new ConfigControllerExample();
        List<ConfigController> configList = configControllerMapper.selectLimit((page-1)*limit,limit);
        Map resultSearch = new HashMap();
        resultSearch.put("code",0);
        resultSearch.put("count",configControllerMapper.countByExample(example));
        resultSearch.put("data",configList);
        resultSearch.put("msg","success");
        return resultSearch;
    }
}
