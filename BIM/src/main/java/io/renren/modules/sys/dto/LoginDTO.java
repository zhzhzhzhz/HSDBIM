package io.renren.modules.sys.dto;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: zh
 * @create: 2019-12-30 15:12
 **/
@Data
public class LoginDTO {
    private Long userId;
    private String username;
    private String password;
    private  String salt;
    private String email;
    private String mobile;
    private Long deptId;
    private Date createTime;
    private String secret_key;
    private String name;













}
