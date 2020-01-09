package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.HsdBimDescriptionDao;
import io.renren.modules.sys.entity.HsdBimDescription;
import io.renren.modules.sys.service.HsdBimDescriptionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zh
 * @create: 2019-12-17 11:20
 **/
@Service
@Transactional
public class HsdBimdescriptionServiceImpl extends ServiceImpl<HsdBimDescriptionDao, HsdBimDescription> implements HsdBimDescriptionService {
    @Autowired
    private HsdBimDescriptionDao hsdBimDescriptionDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        IPage<HsdBimDescription> page = this.page(
                new Query<HsdBimDescription>().getPage(params),
                new QueryWrapper<HsdBimDescription>()
                        .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new PageUtils(page);
    }

    @Override
    public int saveMessage(HsdBimDescription hsdBimDescription) {
        return hsdBimDescriptionDao.saveMessage(hsdBimDescription);
    }

    @Override
    public List<HsdBimDescription> findById(String bid) {
        return hsdBimDescriptionDao.findById(bid);
    }

    @Override
    public List<HsdBimDescription> findType(String type) {
        return hsdBimDescriptionDao.findType(type);
    }

    @Override
    public List<HsdBimDescription> findFloorMessage(String bname) {
        return hsdBimDescriptionDao.findFloorMessage(bname);
    }

    @Override
    public List<HsdBimDescription> findFloor(String bname) {
        return hsdBimDescriptionDao.findFloor(bname);
    }

    @Override
    public List<HsdBimDescription> findHome(String bname) {
        return hsdBimDescriptionDao.findHome(bname);
    }
}
