package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.sys.dao.ToLoginDao;
import io.renren.modules.sys.dto.LoginDTO;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.ToLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: zh
 * @create: 2019-12-30 15:28
 **/
@Service
public class ToLoginServiceImpl extends ServiceImpl<ToLoginDao, LoginDTO> implements ToLoginService{
    @Autowired
    private ToLoginDao toLoginDao;

    @Override
    public LoginDTO userMesssage(String username) {
        return toLoginDao.userMesssage(username);
    }


}
