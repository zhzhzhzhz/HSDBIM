package io.renren.modules.sys.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import io.renren.common.annotation.UserLoginToken;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.dto.HsdPoiDTO;
import io.renren.modules.sys.entity.HsdHotSearch;
import io.renren.modules.sys.entity.HsdPicEntity;
import io.renren.modules.sys.service.HsdHotSearchService;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.sys.entity.HsdPoiEntity;
import io.renren.modules.sys.service.HsdPoiService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 华师大POI
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-12-02 10:18:31
 */
@Slf4j
@RestController
@RequestMapping("sys/hsdpoi")
public class HsdPoiController {

    @Value("${server.port}")
    //获取主机端口
    private String POST;

    @Autowired
    private HsdPoiService hsdPoiService;

    @Autowired
    private HsdHotSearchService hsdHotSearchService;

    @Value("${absoluteImgPath}")
    String absoluteImgPath;
    /**
     * 列表
     */
    @RequestMapping("/list")
    /*@RequiresPermissions("sys:hsdpoi:list")*/
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hsdPoiService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/findAll")
    public R findAll(@RequestParam Map<String, Object> params ){
        String size =(String) params.get("limit");
        int pageSize = Integer.parseInt(size);
        String num =(String) params.get("page");
        int currPage = Integer.parseInt(num);
        int pageNum = (currPage-1)*pageSize;
        String name = (String) params.get("name");
        String order = (String) params.get("order");
        String sidx =(String) params.get("sidx");
        List<HsdPoiDTO> all = null;
        try {
            all = hsdPoiService.findAll(name, order, pageNum, pageSize,sidx);
            if(name != null && name.length() != 0){
                HsdHotSearch hsdHotSearch = new HsdHotSearch();
                hsdHotSearch.setName(name);
                hsdHotSearchService.save(hsdHotSearch);
            }
        }catch (Exception e){
            log.error("查询列表失败"+e);
        }
        int totalCount= hsdPoiService.totalNum(name);
        //int totalPage = (totalCount+pageSize-1)/pageSize;
        PageUtils page = new PageUtils(all,totalCount,pageSize,currPage);
        return R.ok().put("page",page);
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    /*@RequiresPermissions("sys:hsdpoi:info")*/
    public R info(@PathVariable("id") Long id){
        HsdPoiDTO hsdPoi = hsdPoiService.findById(id);
        if(hsdPoi.getTypeMenu() != null) {
            if (hsdPoi.getTypeMenu().equals("生活类")) {
                hsdPoi.setTypeMenu("1");
            }
            if (hsdPoi.getTypeMenu().equals("交通类")) {
                hsdPoi.setTypeMenu("2");
            }
            if (hsdPoi.getTypeMenu().equals("文体类")) {
                hsdPoi.setTypeMenu("3");
            }
            if (hsdPoi.getTypeMenu().equals("单位类")) {
                hsdPoi.setTypeMenu("4");
            }
            if (hsdPoi.getTypeMenu().equals("景观类")) {
                hsdPoi.setTypeMenu("5");
            }
        }
        return R.ok().put("hsdPoi", hsdPoi);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
  //  @RequiresPermissions("sys:hsdpoi:save")
    public Map imageUpload(@RequestParam(value = "filename",required = false) MultipartFile[] files,
                           @RequestParam("name" ) String name,
                           @RequestParam("lng") String lng,
                           @RequestParam("lat") String lat,
                           @RequestParam("tel") String tel,
                           @RequestParam("address") String address,
                           @RequestParam("typeId") Long typeId,
                           @RequestParam("depart") String depart,
                           @RequestParam("jianjie")String jianjie,
                           @RequestParam("history")String history,

                           Model model,HttpServletRequest request){

        //定义一个华师大数据对象
        HsdPoiEntity hsdPoiEntity = new HsdPoiEntity();
        //封装数据
        hsdPoiEntity.setName(name);
        hsdPoiEntity.setLng(lng);
        hsdPoiEntity.setLat(lat);
        hsdPoiEntity.setTel(tel);
        hsdPoiEntity.setAddress(address);
        hsdPoiEntity.setDepart(depart);
        hsdPoiEntity.setJianjie(jianjie);
        hsdPoiEntity.setHistory(history);
        hsdPoiEntity.setTypeId(typeId);
        ///ValidatorUtils.validateEntity(hsdPoiEntity);
        //添加华师大数据并返回主键id（i标识插入数据标识。1：表示插入成功）
        Long i = hsdPoiService.insertData(hsdPoiEntity);

        //添加图片
        addPicture(files,model,hsdPoiEntity,request);

        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    /*@RequiresPermissions("sys:hsdpoi:update")*/
    public R update(@RequestParam(value = "filename",required = false) MultipartFile[] files,
                    @RequestParam("name" ) String name,
                    @RequestParam("lng") String lng,
                    @RequestParam("lat") String lat,
                    @RequestParam("tel") String tel,
                    @RequestParam("address") String address,
                    @RequestParam("typeId") Long typeId,
                    @RequestParam("depart") String depart,
                    @RequestParam("jianjie")String jianjie,
                    @RequestParam("history")String history,
                    @RequestParam("id")Long id,
                    Model model,HttpServletRequest request){
        //定义一个实体对象
        HsdPoiEntity hsdPoiEntity = new HsdPoiEntity();
        //数据封装
        hsdPoiEntity.setId(id);
        hsdPoiEntity.setName(name);
        hsdPoiEntity.setLng(lng);
        hsdPoiEntity.setLat(lat);
        if(tel.equals("null")){
            hsdPoiEntity.setTel("");
        }else {
            hsdPoiEntity.setTel(tel);
        }
        if(tel.equals("null")){
            hsdPoiEntity.setAddress("");
        }else {
            hsdPoiEntity.setAddress(address);
        }
        if(tel.equals("null")){
            hsdPoiEntity.setDepart("");
        }else {
            hsdPoiEntity.setDepart(depart);
        }
        if(tel.equals("null")){
            hsdPoiEntity.setJianjie("");
        }else {
            hsdPoiEntity.setJianjie(jianjie);
        }
        if(tel.equals("null")){
            hsdPoiEntity.setHistory("");
        }else {
            hsdPoiEntity.setHistory(history);
        }
        hsdPoiEntity.setTypeId(typeId);
        ////ValidatorUtils.validateEntity(hsdPoiEntity);
        //保存华师大数据对象
        hsdPoiService.updateById(hsdPoiEntity);
        //添加图片
        addPicture(files,model,hsdPoiEntity,request);

        return R.ok();
    }

    //添加图片方法
    public void addPicture(MultipartFile[] files,Model model,HsdPoiEntity hsdPoiEntity,HttpServletRequest request){
        HsdPicEntity hsdPicEntity = new HsdPicEntity();
        if(files.length != 0){
            for (MultipartFile file : files) {
                //获取上传图片的路径
                Map<String, String> map = hsdPoiService.fileUpload(file, model);
                if(map.get("code") == "200"){

                    //获取项目路径
                    String contextPath = request.getContextPath();
                    //拼接路径
                    String picPath = contextPath+map.get("imgUrl");
                    //封装图片路径
                    hsdPicEntity.setPicture(picPath);
                }
                //封装外键id
                hsdPicEntity.setHid(hsdPoiEntity.getId());

                //保存图片数据
                hsdPoiService.insertPicture(hsdPicEntity);
            }
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:hsdpoi:delete")
    public R delete(@RequestBody Long[] ids){
        hsdPoiService.removeByIds(Arrays.asList(ids));

        //删除华师大数据对应的图片数据
        for (Long id : ids) {
            List<HsdPicEntity> picUrl = hsdPoiService.findPic(id);
            try {
                hsdPoiService.deletePicture(id);
            }catch (Exception e){
                log.error("图片删除失败"+e);
            }

            for(HsdPicEntity item : picUrl){
                String picture = item.getPicture();
                String replaceUrl = picture.replace("/BIM/statics/images/", "");
                File file = new File(absoluteImgPath);
                File[] files = file.listFiles();
                for(File filePath : files){
                    if(filePath.getName().equals(replaceUrl)){
                        filePath.delete();
                    }
                }
            }
        }

        return R.ok();
    }

    /**
     * 根据id获取图片集合
     * @param id
     * @return
     */
    @RequestMapping("/findPic")
    public R findPic(@RequestBody Long id){
        List<HsdPicEntity> pic = hsdPoiService.findPic(id);
        List<String> listUrl = new ArrayList();
        return R.ok().put("hsdPic", pic);
    }

    /**
     * 点击删除图片
     * @param picUrl
     * @return
     */
    @RequestMapping("/clickDelPic")
    public R clickDelPic(@RequestBody String picUrl){
        picUrl = picUrl.substring(1,picUrl.length()-1);  //去除字符串中两边的引号
        String replaceUrl = picUrl.replace("/BIM/statics/images/", "");
        File file = new File(absoluteImgPath);
        File[] files = file.listFiles();
        for(File filePath : files){
            if(filePath.getName().equals(replaceUrl)){
                filePath.delete();
            }
        }
        HsdPicEntity pic = hsdPoiService.getPic(picUrl);  //获取要删除的图片对象
        hsdPoiService.delPic(pic.getId()); //删除当前图片
        List<HsdPicEntity> picList = hsdPoiService.findPic(pic.getHid());  //获取该外键下的其他图片
        return R.ok().put("hsdPic", picList);
    }
    @UserLoginToken
    @RequestMapping("/poiMessage")
    public R poiMessage(@RequestParam("typeName") String typeName,@RequestParam("typeMenu") String typeMenu){
        List<HsdPoiDTO> poiMessage = hsdPoiService.poiMessage(typeName,typeMenu);
        for(HsdPoiDTO hsdPoiDTO : poiMessage){
            Long id = hsdPoiDTO.getId();
            List<HsdPicEntity> pic = hsdPoiService.findPic(id);
            List<String> picList = new ArrayList();
            for(HsdPicEntity hsdPicEntity : pic){
                String picture = hsdPicEntity.getPicture();
                String pictureUrl = "http://192.168.50.16:8080"+picture;
                picList.add(pictureUrl);
            }
            hsdPoiDTO.setPicture(picList);
        }
        return R.ok().put("poiMessage",poiMessage);
    }
}
