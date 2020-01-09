package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.HsdBimDescription;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HsdBimDescriptionDao extends BaseMapper<HsdBimDescription> {
    int saveMessage(HsdBimDescription hsdBimDescription);

    List<HsdBimDescription> findById(String bid);
    List<HsdBimDescription> findType(String type);
    List<HsdBimDescription> findFloorMessage(String bname);
    List<HsdBimDescription> findFloor(String bname);
    List<HsdBimDescription> findHome(String bname);
}
