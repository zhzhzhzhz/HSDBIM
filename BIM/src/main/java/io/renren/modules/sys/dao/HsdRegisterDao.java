package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: zh
 * @create: 2019-12-12 14:24
 **/
@Mapper
@Repository
public interface HsdRegisterDao extends BaseMapper<SysUserEntity> {

}
