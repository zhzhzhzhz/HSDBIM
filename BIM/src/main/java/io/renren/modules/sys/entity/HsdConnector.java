package io.renren.modules.sys.entity;

import lombok.Data;

/**
 * @description: 接口管理
 * @author: zh
 * @create: 2019-12-24 10:28
 **/
@Data
public class HsdConnector {
    private Integer id;
    private String name;
    private String url;
    private String description;
}
