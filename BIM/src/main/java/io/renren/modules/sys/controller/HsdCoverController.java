package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.HsdCover;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.HsdCoverService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * @description: 图层管理
 * @author: zh
 * @create: 2019-12-06 10:21
 **/
@Slf4j
@RestController
@RequestMapping("/sys/hsdCover")
public class HsdCoverController {
    @Value("${server.port}")
    //获取主机端口
    private String POST;

    @Value("${absoluteImgPath}")
    String absoluteImgPath;

    @Value("${sonImgPath}")
    String sonImgPath;

    private String updateImgUrl;

    @Autowired
    private HsdCoverService hsdCoverService;

    @RequestMapping("/queryAll")
    public R list(@RequestParam Map<String,Object> params){
        PageUtils page = hsdCoverService.queryPage(params);
        return R.ok().put("page",page);
    }
    @RequestMapping("/queryMenu")
    public R queryMenu(@RequestBody String typeMenu){
        HsdCover hsdCover = new HsdCover();
        if(typeMenu.equals("1")){
            hsdCover.setTypeMenu("生活类");
        }
        if(typeMenu.equals("2")){
            hsdCover.setTypeMenu("交通类");
        }
        if(typeMenu.equals("3")){
            hsdCover.setTypeMenu("文体类");
        }
        if(typeMenu.equals("4")){
            hsdCover.setTypeMenu("单位类");
        }
        if(typeMenu.equals("5")){
            hsdCover.setTypeMenu("景观类");
        }
        List<HsdCover> hsdcover = hsdCoverService.queryAll(hsdCover);
        return R.ok().put("hsdcover",hsdcover);
    }
    @RequestMapping("/saveMessage")
    public Map saveMessage(@RequestParam(value = "fileName", required = false) MultipartFile file,
                           @RequestParam("typeName") String typeName,
                           @RequestParam("typeMenu") String typeMenu){
        String photoName ="";
        if(file==null){
            photoName="";
        }else {
            photoName = addTypePhoto(file);
        }
        HsdCover hsdCover = new HsdCover();
        Date date = new Date();
        if(typeMenu.equals("1")){
            hsdCover.setTypeMenu("生活类");
        }
        if(typeMenu.equals("2")){
            hsdCover.setTypeMenu("交通类");
        }
        if(typeMenu.equals("3")){
            hsdCover.setTypeMenu("文体类");
        }
        if(typeMenu.equals("4")){
            hsdCover.setTypeMenu("单位类");
        }
        if(typeMenu.equals("5")){
            hsdCover.setTypeMenu("景观类");
        }
        SysUserEntity user= (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        hsdCover.setTypeName(typeName);
        hsdCover.setCreateUser(user.getUsername());
        hsdCover.setTypePhoto(photoName);
        hsdCover.setCreateTime(date);
        hsdCover.setUpdateTime(date);
        ///ValidatorUtils.validateEntity(hsdCover);
        Integer integer = hsdCoverService.saveMessage(hsdCover);
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
            //刚刚修改的地方
            Files.copy(file.getInputStream(), Paths.get(absoluteImgPath, url), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            e.printStackTrace();
            log.error("图片上传失败"+e);
        }
        url ="/BIM"+ sonImgPath + fileName;
        return url;
    }

    @RequestMapping("/updateMessage")
    public Map updateMessage(@RequestParam(value = "fileName",required = false) MultipartFile file,
                             @RequestParam("typeMenu") String typeMenu,
                             @RequestParam("typeName") String typeName,
                             @RequestParam("id") Long id){
        HsdCover hsdCover = new HsdCover();
        if(file != null){
            String photoName = addTypePhoto(file);
            hsdCover.setTypePhoto(photoName);
        }else{
            hsdCover.setTypePhoto("");
        }
        File allFile = new File(absoluteImgPath);
        File[] files = allFile.listFiles();
        for(File filePath : files){
            if(filePath.getName().equals(updateImgUrl)){
                filePath.delete();
            }
        }
        if(typeMenu.equals("1")){
            hsdCover.setTypeMenu("生活类");
        }
        if(typeMenu.equals("2")){
            hsdCover.setTypeMenu("交通类");
        }
        if(typeMenu.equals("3")){
            hsdCover.setTypeMenu("文体类");
        }
        if(typeMenu.equals("4")){
            hsdCover.setTypeMenu("单位类");
        }
        if(typeMenu.equals("5")){
            hsdCover.setTypeMenu("景观类");
        }
        SysUserEntity user= (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        hsdCover.setId(id);
        hsdCover.setTypeName(typeName);
        hsdCover.setCreateUser(user.getUsername());
        hsdCover.setUpdateTime(new Date());
        //ValidatorUtils.validateEntity(hsdCover);
        hsdCoverService.updateById(hsdCover);
        return R.ok();
    }

    @RequestMapping("/findById/{id}")
    public R info(@PathVariable("id") Long id){
        HsdCover hsdcover = hsdCoverService.getById(id);
        updateImgUrl = hsdcover.getTypePhoto().replace("/BIM/statics/images/","");
        if(hsdcover.getTypeMenu().equals("生活类")){
            hsdcover.setTypeMenu("1");
        }
        if(hsdcover.getTypeMenu().equals("交通类")){
            hsdcover.setTypeMenu("2");
        }
        if(hsdcover.getTypeMenu().equals("文体类")){
            hsdcover.setTypeMenu("3");
        }
        if(hsdcover.getTypeMenu().equals("单位类")){
            hsdcover.setTypeMenu("4");
        }
        if(hsdcover.getTypeMenu().equals("景观类")){
            hsdcover.setTypeMenu("5");
        }
        return R.ok().put("hsdcover",hsdcover);
    }

    @RequestMapping("deleteMessage")
    public R deleteAll(@RequestBody Long[] ids){
        List listIds = new ArrayList(Arrays.asList(ids));
        List<HsdCover> list1 = hsdCoverService.queryImages(listIds);
        hsdCoverService.removeByIds(Arrays.asList(ids));
        for(HsdCover item : list1){
            String typePhoto = item.getTypePhoto();
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
