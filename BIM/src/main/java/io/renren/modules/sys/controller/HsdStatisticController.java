package io.renren.modules.sys.controller;

import io.renren.common.annotation.UserLoginToken;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.HsdStatistic;
import io.renren.modules.sys.service.HsdStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description: 区域统计分析
 * @author: zh
 * @create: 2019-12-12 16:28
 **/
@Slf4j
@RestController
@RequestMapping("/sys/statistic")
public class HsdStatisticController {

    @Autowired
    private HsdStatisticService hsdStatisticService;
    @UserLoginToken
    @RequestMapping("/saveMessage")
    public R save(@RequestBody HsdStatistic hsdStatistic){
        hsdStatisticService.save(hsdStatistic);
        return R.ok();
    }
    @UserLoginToken
    @RequestMapping("/queryPeopleArea")
    public R query(@RequestParam(value = "startTime",required = false) Date startTime,
                   @RequestParam(value = "endTime",required = false)Date endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start = null;
        String end = null;
        if(startTime != null && endTime != null) {
            start = sdf.format(startTime);
            end = sdf.format(endTime);
        }
        List<HsdStatistic>  hsdStatisticsList = hsdStatisticService.queryPeopleArea(start, end);

        return R.ok().put("hsdStatisticsList",hsdStatisticsList);
    }
}
