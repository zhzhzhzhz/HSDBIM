package io.renren.modules.sys.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: zh
 * @create: 2019-12-12 16:33
 **/
@Data
public class HsdStatistic {
    //活跃用户 id
    private Long id;
    //活跃用户
    private String username;
    //活跃地区经纬度
    private String userLng;
    private String userLat;
    //登录时间
    private Date createTime;

}
