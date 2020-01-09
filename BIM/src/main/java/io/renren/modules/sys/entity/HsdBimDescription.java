package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @description: BIM描述
 * @author: zh
 * @create: 2019-12-17 11:16
 **/
@Data
public class HsdBimDescription {
    private Long id;
    private String bid;
    private String name;
    private String title;
    private String floor;
    private String bname;
    private String description;
    private String type;
    @TableField(exist = false)
    private Object data;
}
