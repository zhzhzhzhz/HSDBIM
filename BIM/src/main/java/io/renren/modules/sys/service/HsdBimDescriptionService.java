package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.HsdBimDescription;

import java.util.List;
import java.util.Map;

public interface HsdBimDescriptionService extends IService<HsdBimDescription> {
    PageUtils queryPage(Map<String, Object> params);

    int saveMessage(HsdBimDescription hsdBimDescription);

    List<HsdBimDescription> findById(String bid);

    List<HsdBimDescription> findType(String type);

    List<HsdBimDescription> findFloorMessage(String type);

    List<HsdBimDescription> findFloor(String bname);

    List<HsdBimDescription> findHome(String bname);
}
