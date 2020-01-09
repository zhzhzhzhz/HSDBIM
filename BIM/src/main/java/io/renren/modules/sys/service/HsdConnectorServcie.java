package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.HsdConnector;

import java.util.Map;

public interface HsdConnectorServcie extends IService<HsdConnector> {
    PageUtils queryPage(Map<String, Object> params);
}
