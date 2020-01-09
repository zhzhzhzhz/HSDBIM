package io.renren.modules.sys.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description: 登录记录
 * @author: zh
 * @create: 2019-12-13 09:42
 **/
@Data
public class HsdLoginRecord {
    private Long id;
    //登录用户名
    private String username;
    //登录时间
    private Date createTime;
}
