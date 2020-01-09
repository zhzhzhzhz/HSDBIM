package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.modules.sys.entity.HsdStatistic;
import java.util.List;

public interface HsdStatisticService extends IService<HsdStatistic> {
    List<HsdStatistic> queryPeopleArea(String start, String end);
}
