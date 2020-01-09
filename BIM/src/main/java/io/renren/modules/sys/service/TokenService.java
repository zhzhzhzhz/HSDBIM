package io.renren.modules.sys.service;

import io.renren.modules.sys.dto.LoginDTO;


public interface TokenService {
    public String getToken(LoginDTO userForBase);

}
