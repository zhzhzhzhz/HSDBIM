package io.renren.modules.sys.controller;

import io.renren.common.annotation.UserLoginToken;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.HsdBimDescription;
import io.renren.modules.sys.service.HsdBimDescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @description: BIM描述
 * @author: zh
 * @create: 2019-12-17 11:14
 **/
@Slf4j
@RestController
@RequestMapping("/sys/hsdBimDescription")
public class HsdBimDescriptionController {
    @Autowired
    private HsdBimDescriptionService hsdBimDescriptionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hsdBimDescriptionService.queryPage(params);
        List<HsdBimDescription> list = (List<HsdBimDescription>) page.getList();
        for(HsdBimDescription hsdBimDescription : list){
            if(hsdBimDescription.getType() !=null) {
                if (hsdBimDescription.getType().equals("1")) {
                    hsdBimDescription.setType("楼");
                }
                if (hsdBimDescription.getType().equals("2")) {
                    hsdBimDescription.setType("楼层");
                }
                if (hsdBimDescription.getType().equals("3")) {
                    hsdBimDescription.setType("房间");
                }
            }else {
                hsdBimDescription.setType("");
            }
        }
        return R.ok().put("page", page);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestParam("bid") String bid,
                  @RequestParam("name") String name,
                  @RequestParam("title") String title,
                  @RequestParam("floor") String floor,
                  @RequestParam("bname") String bname,
                  @RequestParam("type") String type,
                  @RequestParam("description") String description){
        HsdBimDescription hsdBimDescription = new HsdBimDescription();
        hsdBimDescription.setBid(bid);
        hsdBimDescription.setName(name);
        hsdBimDescription.setTitle(title);
        hsdBimDescription.setFloor(floor);
        hsdBimDescription.setBname(bname);
        if(type.equals("楼")){
            hsdBimDescription.setType("1");
        }
        if(type.equals("楼层")){
            hsdBimDescription.setType("2");
        }
        if(type.equals("房间")){
            hsdBimDescription.setType("3");
        }

        hsdBimDescription.setDescription(description);
        try {
            hsdBimDescriptionService.save(hsdBimDescription);
        }catch (Exception e){
            log.error("保存操作失败"+e);
            e.printStackTrace();
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        hsdBimDescriptionService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        HsdBimDescription hsdBim = hsdBimDescriptionService.getById(id);
        if(hsdBim.getType().equals("1")){
            hsdBim.setType("楼");
        }
        else if(hsdBim.getType().equals("2")){
            hsdBim.setType("楼层");
        }
        else if(hsdBim.getType().equals("3")){
            hsdBim.setType("房间");
        }
        else {
            hsdBim.setType("");
        }
        return R.ok().put("hsdBim", hsdBim);
    }
    /**
     * 信息  对外开发的接口
     */
    @UserLoginToken
    @RequestMapping("/findById")
    public R findById(@RequestParam("bid") String bid){
        List<HsdBimDescription> hsdBimList = hsdBimDescriptionService.findById(bid);
        return R.ok().put("hsdBim", hsdBimList);
    }
    @UserLoginToken
    @RequestMapping("/findType")
    public R findType(@RequestParam("type") String type){
        List<HsdBimDescription> hsdBimList = hsdBimDescriptionService.findType(type);
        return R.ok().put("hsdBim", hsdBimList);
    }
    @UserLoginToken
    @RequestMapping("/findFloorMessage")
    public R findFloorMessage(@RequestParam("bname") String bname){
        List<HsdBimDescription> hsdBimList = hsdBimDescriptionService.findFloorMessage(bname);
        List<HsdBimDescription> floorList = hsdBimDescriptionService.findFloor(bname);
        List<HsdBimDescription> homeList = hsdBimDescriptionService.findHome(bname);
        for(HsdBimDescription hsdBimDescription : hsdBimList){
            hsdBimDescription.setData(floorList);
            for(HsdBimDescription floor : floorList){
                List<HsdBimDescription> list  = new ArrayList<>();
                String floor1 = floor.getFloor();
                for(HsdBimDescription home : homeList){
                    if(home.getFloor().equals(floor1)){
                        list.add(home);
                    }
                }
                floor.setData(list);
            }
        }
        return R.ok().put("hsdBim", hsdBimList);
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestParam("id") Long id,
                    @RequestParam("bid") String bid,
                    @RequestParam("name") String name,
                    @RequestParam("title") String title,
                    @RequestParam("floor") String floor,
                    @RequestParam("bname") String bname,
                    @RequestParam("type") String type,
                    @RequestParam("description") String description){
        HsdBimDescription hsdBimDescription = new HsdBimDescription();
        hsdBimDescription.setId(id);
        hsdBimDescription.setBid(bid);
        hsdBimDescription.setName(name);
        hsdBimDescription.setTitle(title);
        hsdBimDescription.setFloor(floor);
        hsdBimDescription.setBname(bname);
        if(type.equals("楼")){
            hsdBimDescription.setType("1");
        }
        else if(type.equals("楼层")){
            hsdBimDescription.setType("2");
        }
        else if(type.equals("房间")){
            hsdBimDescription.setType("3");
        }
        else {
            hsdBimDescription.setType("");
        }
        hsdBimDescription.setDescription(description);
        hsdBimDescriptionService.updateById(hsdBimDescription);
        return R.ok();
    }
}
