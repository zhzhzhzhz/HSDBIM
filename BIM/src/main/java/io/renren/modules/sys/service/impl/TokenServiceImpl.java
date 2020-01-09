package io.renren.modules.sys.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import io.renren.modules.sys.dto.LoginDTO;
import io.renren.modules.sys.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @description:
 * @author: zh
 * @create: 2019-12-31 09:42
 **/
@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String getToken(LoginDTO userForBase) {

        // 生成过期时间
        String token = JWT.create()
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+3*60*60*1000))
                .withAudience(userForBase.getUserId().toString())
                .sign(Algorithm.HMAC256(userForBase.getPassword()));


        return token;
    }


}
