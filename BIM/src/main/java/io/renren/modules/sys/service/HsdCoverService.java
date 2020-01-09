package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.HsdCover;
import io.renren.modules.sys.entity.HsdPoiEntity;

import java.util.List;
import java.util.Map;

public interface HsdCoverService extends IService<HsdCover> {
    public List<HsdCover> queryAll(HsdCover hsdCover);

    public Integer saveMessage(HsdCover hsdCover);

    public Integer updateMessage(HsdCover hsdCover);

    public Integer deleteMessage(Long id);

    public Integer delelteBatches(List<String> Ids);

    PageUtils queryPage(Map<String, Object> params);

    public List queryImages(List<String> Ids);
}
