package io.renren.modules.sys.controller;

import io.renren.common.annotation.UserLoginToken;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.HsdRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @description: 用户注册
 * @author: zh
 * @create: 2019-12-12 14:20
 **/
@Slf4j
@RestController
@RequestMapping("/sys/register")
public class HsdRegisterController extends AbstractController{
    
    @Autowired
    private HsdRegisterService hsdRegisterService;

    @UserLoginToken
    @RequestMapping("/user")
    public R register(@RequestBody SysUserEntity sysUserEntity){
        /*String str = UUID.randomUUID()+"";
        sysUserEntity.setSecretKey(str);*/
        hsdRegisterService.saveUser(sysUserEntity);
        return R.ok();
    }


}
