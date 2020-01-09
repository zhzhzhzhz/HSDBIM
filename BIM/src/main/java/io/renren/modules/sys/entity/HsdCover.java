package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;


/**
 * @description: 图层分类
 * @author: zh
 * @create: 2019-12-06 10:15
 **/
@Data
@TableName("hsd_cover")
public class HsdCover implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private Long id;
    @NotBlank(message="所属分类不能为空")
    private String typeMenu;
    @NotBlank(message="所属图层不能为空")
    private String typeName;
    private String typePhoto;
    //@NotBlank(message="创建人不能为空")
    private String createUser;
    private Date createTime;
    private Date updateTime;

}
