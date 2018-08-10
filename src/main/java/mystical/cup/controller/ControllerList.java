package mystical.cup.controller;

import com.google.gson.Gson;
import mystical.cup.dao.mapper.ConfigControllerMapper;
import mystical.cup.model.database.ConfigController;
import mystical.cup.model.database.ConfigControllerExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Mystical on 2018/8/10.
 */
@RestController
public class ControllerList {

    @Autowired
    private ConfigControllerMapper configControllerMapper;

    @GetMapping("/controller/list")
    public String listController() {
        ConfigControllerExample example = new ConfigControllerExample();
        List<ConfigController> configList = configControllerMapper.selectByExample(example);
        return new Gson().toJson(configList);
    }
}
