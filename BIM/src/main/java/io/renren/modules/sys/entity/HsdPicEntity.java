package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by Administrator on 2019/12/2.
 */

@Data
@TableName("hsd_pic")
public class HsdPicEntity {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /**
     * 图片
     */
    @TableField("picture")
    private String picture;

    /**
     * 外键
     */
    @TableField("hid")
    private Long hid;

}
