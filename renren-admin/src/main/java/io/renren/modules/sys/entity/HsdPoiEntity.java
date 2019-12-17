package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * 华师大POI
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-12-02 10:18:31
 */
@Data
@TableName("hsd_poi")
public class HsdPoiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 名称
	 */
	@NotBlank(message="名称不能为空")
	private String name;
	/**
	 * 经度
	 */
	@NotBlank(message="经度不能为空")
	private String lng;
	/**
	 * 纬度
	 */
	@NotBlank(message="维度不能为空")
	private String lat;
	/**
	 * 电话
	 */
	//@NotBlank(message="电话不能为空")
	private String tel;
	/**
	 * 地址
	 */
	///@NotBlank(message="地址不能为空")
	private String address;

	@NotNull(message="所属图层不能为空")
	private Long typeId;
	/**
	 * 类型
	 */
	///@NotBlank(message="类型不能为空")
	private String type;
	/**
	 * 部门
	 */
	//@NotBlank(message="部门不能为空")
	private String depart;
	/**
	 * 简介
	 */
	//@NotBlank(message="简介不能为空")
	private String jianjie;
	/**
	 * 历史
	 */
	//@NotBlank(message="历史不能为空")
	private String history;



	/**
	 * 图片上传
	 */
	/*@TableField(exist = false)
	private List<HsdPicEntity> hsdPicEntities;*/
}
