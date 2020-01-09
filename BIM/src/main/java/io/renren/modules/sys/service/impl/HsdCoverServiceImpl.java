package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.HsdCoverDao;
import io.renren.modules.sys.dao.HsdPoiDao;
import io.renren.modules.sys.entity.HsdCover;
import io.renren.modules.sys.entity.HsdPoiEntity;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.HsdCoverService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description: 图层管理
 * @author: zh
 * @create: 2019-12-06 10:23
 **/
@Service
public class HsdCoverServiceImpl extends ServiceImpl<HsdCoverDao, HsdCover>  implements HsdCoverService {
    @Autowired
    private HsdCoverDao hsdCoverDao;

    @Override
    public List<HsdCover> queryAll(HsdCover hsdCover) {
        List<HsdCover> hsdCovers = hsdCoverDao.queryAll(hsdCover);
        return hsdCovers;
    }

    @Override
    public Integer saveMessage(HsdCover hsdCover) {
        Integer integer = hsdCoverDao.saveMessage(hsdCover);
        return integer;
    }

    @Override
    public Integer updateMessage(HsdCover hsdCover) {
        return hsdCoverDao.updateMessage(hsdCover);
    }

    @Override
    public Integer deleteMessage(Long id) {
        return hsdCoverDao.deleteMessage(id);
    }

    @Override
    public Integer delelteBatches(List<String> Ids) {
        return hsdCoverDao.delelteBatches(Ids);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String typeName = (String) params.get("typeName");
        IPage<HsdCover> page = this.page(
                new Query<HsdCover>().getPage(params),
                new QueryWrapper<HsdCover>().like(StringUtils.isNotBlank(typeName),"type_name",typeName)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

    @Override
    public List queryImages(List<String> Ids) {
        return hsdCoverDao.queryImages(Ids);
    }
}
