package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.sys.dto.LoginDTO;
import io.renren.modules.sys.entity.SysUserEntity;

public interface ToLoginService extends IService<LoginDTO> {
    LoginDTO userMesssage(String username);



}
