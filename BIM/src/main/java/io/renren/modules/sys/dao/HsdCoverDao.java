package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.HsdCover;
import io.renren.modules.sys.entity.HsdPoiEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HsdCoverDao extends BaseMapper<HsdCover> {
    public List<HsdCover> queryAll(HsdCover hsdCover);

    public Integer saveMessage(HsdCover hsdCover);

    public Integer updateMessage(HsdCover hsdCover);

    public Integer deleteMessage(Long id);

    public Integer delelteBatches(List<String> Ids);

    public List queryImages(List<String> Ids);
}
