package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.sys.entity.SysUserEntity;

public interface HsdRegisterService extends IService<SysUserEntity> {
    void saveUser(SysUserEntity user);
}
