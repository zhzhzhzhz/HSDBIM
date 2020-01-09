package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.sys.dao.SysLoginDao;
import io.renren.modules.sys.entity.HsdLoginRecord;
import io.renren.modules.sys.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: zh
 * @create: 2019-12-13 09:55
 **/
@Service
public class SysLoginServiceImpl extends  ServiceImpl<SysLoginDao, HsdLoginRecord> implements SysLoginService {
    @Autowired
    private SysLoginDao sysLoginDao;
    @Override
    public int queryPeopleNum(String start, String end) {
        return sysLoginDao.queryPeopleNum(start,end);
    }
}
