package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.sys.dao.HsdCoverDao;
import io.renren.modules.sys.dao.HsdStatisticDao;
import io.renren.modules.sys.entity.HsdStatistic;
import io.renren.modules.sys.service.HsdStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: zh
 * @create: 2019-12-12 16:38
 **/
@Service
public class HsdStatisticServiceImpl extends ServiceImpl<HsdStatisticDao, HsdStatistic> implements HsdStatisticService {
    @Autowired
    private HsdStatisticDao hsdStatisticDao;

    @Override
    public List<HsdStatistic> queryPeopleArea(String start, String end) {
        return hsdStatisticDao.queryPeopleArea(start, end);
    }
}
