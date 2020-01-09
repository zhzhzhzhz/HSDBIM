package io.renren.modules.sys.controller;

import io.renren.common.annotation.UserLoginToken;
import io.renren.common.utils.R;
import io.renren.modules.sys.service.HsdHotSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @description: 热门搜索
 * @author: zh
 * @create: 2019-12-13 12:05
 **/
@Slf4j
@RestController
@RequestMapping("/sys/hotSearch")
public class HsdHotSearchController {
    @Autowired
    private HsdHotSearchService hsdHotSearchService;
    @UserLoginToken
    @RequestMapping("/queryHotSearch")
    public R query(){
        List<Map<String,Object>> hotSearchMap  = null;
        hotSearchMap = hsdHotSearchService.queryHotSearch();
        return R.ok().put("hotSearchMap",hotSearchMap);
    }

}
