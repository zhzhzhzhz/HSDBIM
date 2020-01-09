package io.renren.modules.sys.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: zh
 * @create: 2019-12-31 17:23
 **/
public class JwtUtils {
    private String secret = "a1g2y47dg3dj59fjhhsd7cnewy73j";
    //private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


    public Date generateExpirationDate(){
        Date generateExpirationDate = new Date(System.currentTimeMillis() + 1*1000);
        return generateExpirationDate;
    }
    public Date generateCurrentDate(){
        Date generateCurrentDate = new Date(System.currentTimeMillis());
        return generateCurrentDate;
    }
    /**
     * 初始化生成token的参数
     * @param userId
     * @return String
     */
    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>(1);
        claims.put("userId", userId);
        return generateToken(claims);
    }

    /**
     * 生成token
     * @param claims
     * @return String
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(this.generateExpirationDate())
                .setIssuedAt(this.generateCurrentDate())
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }
    //判断token是否可以刷新
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
            final Date iat = claims.getIssuedAt();
            final Date exp = claims.getExpiration();
            if (iat.before(lastPasswordReset) || exp.before(generateCurrentDate())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    ///刷新token
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
            refreshedToken = this.generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
    //校验token
    public String verifyToken(String token) {
        String result = "";
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
            result = "有效的";
        } catch (Exception e) {
            result = "无效的";
        }
        return result;
    }

}
