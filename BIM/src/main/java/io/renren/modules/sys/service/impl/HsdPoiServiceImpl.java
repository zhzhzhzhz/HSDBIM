package io.renren.modules.sys.service.impl;

import io.renren.common.utils.Constant;
import io.renren.modules.sys.dto.HsdPoiDTO;
import io.renren.modules.sys.entity.HsdPicEntity;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.HsdPoiDao;
import io.renren.modules.sys.entity.HsdPoiEntity;
import io.renren.modules.sys.service.HsdPoiService;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;


@Service("hsdPoiService")
public class HsdPoiServiceImpl extends ServiceImpl<HsdPoiDao, HsdPoiEntity> implements HsdPoiService {

    @Value("${absoluteImgPath}")
    String absoluteImgPath;

    @Value("${sonImgPath}")
    String sonImgPath;


    @Autowired
    private HsdPoiDao hsdPoiDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        IPage<HsdPoiEntity> page = this.page(
                new Query<HsdPoiEntity>().getPage(params),
                new QueryWrapper<HsdPoiEntity>().like(StringUtils.isNotBlank(name),"name",name)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        /*for(HsdPoiEntity hsdPoiEntity : page.getRecords()){
            SysDeptEntity sysDeptEntity = sysDeptService.getById(sysUserEntity.getDeptId());
            sysUserEntity.setDeptName(sysDeptEntity.getName());
        }*/

        return new PageUtils(page);
    }

    @Override
    public Map<String, String> fileUpload(MultipartFile file,Model model) {
        Map<String,String> map = new HashMap<>();
        if(file.isEmpty()){
            map.put("code","500");
            map.put("msg","图片不能为空");
            return map;
        }
        File dest = new File(absoluteImgPath);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        String imgUrl = sonImgPath +fileName;
        try {
            Files.copy(file.getInputStream(), Paths.get(absoluteImgPath, fileName), StandardCopyOption.REPLACE_EXISTING);
            map.put("code","200");
            map.put("msg","上传成功");
            map.put("imgUrl",imgUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("filename", imgUrl);
        return map;

    }

    //插入华师大表单提交数据
    @Override
    public Long insertData(HsdPoiEntity hsdPoiEntity) {
        Long i = hsdPoiDao.insertData(hsdPoiEntity);
        return i;
    }

    //插入华师大图片
    @Override
    public void insertPicture(HsdPicEntity hsdPicEntity) {
        hsdPoiDao.insertPictrue(hsdPicEntity);
    }

    //删除华师大图片
    @Override
    public void deletePicture(Long id) {
        hsdPoiDao.deletePicture(id);
    }

    //查询图片
    @Override
    public List<HsdPicEntity> findPic(Long id) {
        return hsdPoiDao.findPic(id);
    }

    //点击删除图片
    @Override
    public void delPic(Long id) {
        hsdPoiDao.delPic(id);
    }

    //根据名称查询图片数据
    @Override
    public HsdPicEntity getPic(String picUrl) {
        return hsdPoiDao.getPic(picUrl);
    }

    @Override
    public HsdPoiDTO findById(Long id) {
       return hsdPoiDao.findById(id);
    }

    @Override
    public List<HsdPoiDTO> findAll(String name,String order,int pageNum,int pageSize,String sidx) {
        List<HsdPoiDTO> allList = hsdPoiDao.findAll(name,order,pageNum,pageSize,sidx);
        return allList;
    }

    @Override
    public int totalNum(String name) {
        return hsdPoiDao.totalNum(name);
    }

    @Override
    public List<HsdPoiDTO> poiMessage(String typeName,String typeMenu) {
        return hsdPoiDao.poiMessage(typeName,typeMenu);
    }

   /* private String saveUploadFile(String filePathNew) {
        //获取本机IP
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        *//*UplocalFileEntity uplocalFileEntity = new UplocalFileEntity();
        uplocalFileEntity.setCreateTime(System.currentTimeMillis());
        uplocalFileEntity.setUpdateTime(System.currentTimeMillis());
        uplocalFileEntity.setEnabled(1);
        uplocalFileEntity.setProfilePhoto(host + ":" + POST + filePathNew);*//*

       *//* Integer result = uplocalFileEntityMapper.insertSelective(uplocalFileEntity);
        System.out.println("插入了"  + result + "数据");

        System.out.println("uplocalFileEntity.getProfilePhoto():" + uplocalFileEntity.getProfilePhoto());
        return uplocalFileEntity.getProfilePhoto();*//*
        return null;
    }*/



}
