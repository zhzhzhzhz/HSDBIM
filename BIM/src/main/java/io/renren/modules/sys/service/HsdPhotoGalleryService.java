package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.HsdPhotoGallery;

import java.util.List;
import java.util.Map;

public interface HsdPhotoGalleryService extends IService<HsdPhotoGallery> {
    PageUtils queryPage(Map<String, Object> params);

    public List queryImages(List<String> Ids);
}
