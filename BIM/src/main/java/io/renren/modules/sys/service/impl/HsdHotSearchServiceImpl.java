package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.sys.dao.HsdHotSearchDao;
import io.renren.modules.sys.entity.HsdHotSearch;
import io.renren.modules.sys.service.HsdHotSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zh
 * @create: 2019-12-13 13:04
 **/
@Service
public class HsdHotSearchServiceImpl extends ServiceImpl<HsdHotSearchDao, HsdHotSearch> implements HsdHotSearchService {

    @Autowired
    private HsdHotSearchDao hsdHotSearchDao;
    @Override
    public List<Map<String,Object>> queryHotSearch() {
        return hsdHotSearchDao.queryHotSearch();
    }
}
