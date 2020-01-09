package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.dto.LoginDTO;
import io.renren.modules.sys.entity.HsdLoginRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysLoginDao extends BaseMapper<HsdLoginRecord> {
    int queryPeopleNum(@Param("start") String start,@Param("end") String end);
}
