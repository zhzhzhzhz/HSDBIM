package io.renren.modules.sys.dao;

import io.renren.modules.sys.dto.HsdPoiDTO;
import io.renren.modules.sys.entity.HsdPicEntity;
import io.renren.modules.sys.entity.HsdPoiEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 华师大POI
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-12-02 10:18:31
 */
@Mapper
@Repository
public interface HsdPoiDao extends BaseMapper<HsdPoiEntity> {

    //插入华师大表单数据数据
    Long insertData(HsdPoiEntity hsdPoiEntity);

    //保存华师大图片
    void insertPictrue(HsdPicEntity hsdPicEntity);

    //删除图片
    void deletePicture(Long id);

    //查询图片
    List<HsdPicEntity> findPic(Long id);

    //删除图片
    void delPic(Long id);

    //根据名称查询图片数据
    HsdPicEntity getPic(String picUrl);

    //修改时回显数据
    HsdPoiDTO findById(Long id);

    List<HsdPoiDTO> findAll(@Param("name") String name,@Param("order") String order,
                            @Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
                            @Param("sidx") String sidx);
    int totalNum(@Param("name") String name);

    List<HsdPoiDTO> poiMessage(@Param("typeName") String typeName,@Param("typeMenu") String typeMenu);
}
