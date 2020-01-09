package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.HsdPhotoGallery;
import io.renren.modules.sys.service.HsdPhotoGalleryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * @description: 图片库
 * @author: zh
 * @create: 2019-12-18 11:57
 **/
@Slf4j
@RestController
@RequestMapping("/sys/photoGallery")
public class HsdPhotoGalleryController {
    @Autowired
    private HsdPhotoGalleryService hsdPhotoGalleryService;

    @Value("${absoluteImgPath}")
    String absoluteImgPath;

    @Value("${sonImgPath}")
    String sonImgPath;

    private String updateImgUrl;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hsdPhotoGalleryService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        HsdPhotoGallery hsdPhoto = hsdPhotoGalleryService.getById(id);
        updateImgUrl = hsdPhoto.getUrl().replace("/BIM/statics/images/","");
        return R.ok().put("hsdPhoto", hsdPhoto);
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public R save(@RequestParam(value = "fileName") MultipartFile file,@RequestParam("name") String name){
        //校验类型
        //ValidatorUtils.validateEntity(dict);
        String photoName ="";
        if(file==null){
            photoName="";
        }else {
            photoName = addTypePhoto(file);
        }
        HsdPhotoGallery hsdPhotoGallery = new HsdPhotoGallery();
        hsdPhotoGallery.setName(name);
        hsdPhotoGallery.setUrl(photoName);
        hsdPhotoGalleryService.save(hsdPhotoGallery);
        return R.ok();
    }
    private String addTypePhoto(MultipartFile file) {
        File targetFile = new File(absoluteImgPath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        String url ="";
        try{
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            //String filePath = absoluteImgPath; // 上传后的路径
            fileName = UUID.randomUUID() + suffixName; // 新文件名
            url = fileName;
            Files.copy(file.getInputStream(), Paths.get(absoluteImgPath, url), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            e.printStackTrace();
            log.error("图片上传失败"+e);
        }
        url ="/BIM"+ sonImgPath + fileName;
        return url;
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestParam(value = "fileName") MultipartFile file,@RequestParam("name") String name,@RequestParam("id") Long id){
        //校验类型
        //ValidatorUtils.validateEntity(dict);
        HsdPhotoGallery hsdPhotoGallery = new HsdPhotoGallery();
        if(file != null){
            String photoName = addTypePhoto(file);
            hsdPhotoGallery.setUrl(photoName);
        }else{
            hsdPhotoGallery.setUrl("");
        }
        File allFile = new File(absoluteImgPath);
        File[] files = allFile.listFiles();
        for(File filePath : files){
            if(filePath.getName().equals(updateImgUrl)){
                filePath.delete();
            }
        }
        hsdPhotoGallery.setName(name);
        hsdPhotoGallery.setId(id);
        hsdPhotoGalleryService.updateById(hsdPhotoGallery);

        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        List listIds = new ArrayList(Arrays.asList(ids));
        List<HsdPhotoGallery> list1 = hsdPhotoGalleryService.queryImages(listIds);
        hsdPhotoGalleryService.removeByIds(Arrays.asList(ids));
        for(HsdPhotoGallery item : list1){
            String typePhoto = item.getUrl();
            String replaceUrl = typePhoto.replace("/BIM/statics/images/", "");
            File file = new File(absoluteImgPath);
            File[] files = file.listFiles();
            for(File filePath : files){
                if(filePath.getName().equals(replaceUrl)){
                    filePath.delete();
                }
            }
        }
        return R.ok();
    }
}
