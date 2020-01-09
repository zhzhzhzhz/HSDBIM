package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.HsdBimDescriptionDao;
import io.renren.modules.sys.dao.HsdPhotoGalleryDao;
import io.renren.modules.sys.entity.HsdPhotoGallery;
import io.renren.modules.sys.service.HsdPhotoGalleryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zh
 * @create: 2019-12-18 12:01
 **/
@Service
public class HsdPhotoGalleryServiceImpl extends ServiceImpl<HsdPhotoGalleryDao, HsdPhotoGallery> implements HsdPhotoGalleryService {
    @Autowired
    private HsdPhotoGalleryDao hsdPhotoGalleryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        IPage<HsdPhotoGallery> page = this.page(
                new Query<HsdPhotoGallery>().getPage(params),
                new QueryWrapper<HsdPhotoGallery>()
                        .like(StringUtils.isNotBlank(name),"name", name)
        );
        return new PageUtils(page);
    }

    @Override
    public List queryImages(List<String> Ids) {
        return hsdPhotoGalleryDao.queryImages(Ids);
    }
}
