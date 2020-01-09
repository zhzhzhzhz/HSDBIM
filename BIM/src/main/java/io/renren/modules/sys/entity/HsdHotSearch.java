package io.renren.modules.sys.entity;

import lombok.Data;

/**
 * @description: 热词搜索
 * @author: zh
 * @create: 2019-12-13 12:59
 **/
@Data
public class HsdHotSearch {
    private Long id;
    //热词
    private String name;
    //搜素时间
    private String createTime;
}
