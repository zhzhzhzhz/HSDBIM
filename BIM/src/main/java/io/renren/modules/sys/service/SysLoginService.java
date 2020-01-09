package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.sys.entity.HsdLoginRecord;

public interface SysLoginService extends IService<HsdLoginRecord> {
    int queryPeopleNum(String start,String end);
}
