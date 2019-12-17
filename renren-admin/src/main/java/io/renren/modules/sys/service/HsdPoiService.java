package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.dto.HsdPoiDTO;
import io.renren.modules.sys.entity.HsdPicEntity;
import io.renren.modules.sys.entity.HsdPoiEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 华师大POI
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-12-02 10:18:31
 */
public interface HsdPoiService extends IService<HsdPoiEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //图片上传
    Map<String,String> fileUpload(MultipartFile file,Model model);

    //插入华师大表单数据
    Long insertData(HsdPoiEntity hsdPoiEntity);

    //插入华师大图片
    void insertPicture(HsdPicEntity hsdPicEntity);

    //删除图片
    void deletePicture(Long id);

    //查询图片
    List<HsdPicEntity> findPic(Long id);

    //点击删除图片执行
    void delPic(Long id);

    //根据名称查询图片数据
    HsdPicEntity getPic(String picUrl);

    //修改时回显数据
    HsdPoiDTO findById(Long id);

    List<HsdPoiDTO> findAll(String name,String order,int pageNum,int pageSize);
    int totalNum(String name);
}

