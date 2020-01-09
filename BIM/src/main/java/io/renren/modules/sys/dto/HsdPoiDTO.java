package io.renren.modules.sys.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: zh
 * @create: 2019-12-10 18:18
 **/
@Data
public class HsdPoiDTO implements Serializable {
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 经度
     */
    private String lng;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 电话
     */
    private String tel;
    /**
     * 地址
     */
    private String address;
    /**
     * 类型
     */
    private String type;
    /**
     * 部门
     */
    private String depart;
    /**
     * 简介
     */
    private String jianjie;
    /**
     * 历史
     */
    private String history;
    /**
     * 外键
     */
    private Long typeId;
    /**
     * 图层所属类型
     */
    private String typeMenu;
    /**
     * 类型
     */
    private String typeName;
    /**
     * 图片
     */
    private List<String> picture;

}
