package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.HsdPhotoGallery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HsdPhotoGalleryDao extends BaseMapper<HsdPhotoGallery> {

    public List queryImages(List<String> Ids);
}
