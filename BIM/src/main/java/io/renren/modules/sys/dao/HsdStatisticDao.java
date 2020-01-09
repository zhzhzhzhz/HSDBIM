package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.HsdStatistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface HsdStatisticDao extends BaseMapper<HsdStatistic> {

    List<HsdStatistic> queryPeopleArea(@Param("start")String startTime, @Param("end")String endTime);

}
