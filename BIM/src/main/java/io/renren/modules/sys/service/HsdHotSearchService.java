package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.sys.entity.HsdHotSearch;

import java.util.List;
import java.util.Map;

public interface HsdHotSearchService extends IService<HsdHotSearch> {
    List<Map<String,Object>> queryHotSearch();
}
