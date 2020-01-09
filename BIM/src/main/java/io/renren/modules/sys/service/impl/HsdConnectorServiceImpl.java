package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.HsdConnectorDao;
import io.renren.modules.sys.entity.HsdConnector;
import io.renren.modules.sys.entity.SysDictEntity;
import io.renren.modules.sys.service.HsdConnectorServcie;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description:
 * @author: zh
 * @create: 2019-12-24 10:42
 **/
@Service
public class HsdConnectorServiceImpl extends ServiceImpl<HsdConnectorDao, HsdConnector> implements HsdConnectorServcie {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        IPage<HsdConnector> page = this.page(
                new Query<HsdConnector>().getPage(params),
                new QueryWrapper<HsdConnector>()
                        .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new PageUtils(page);
    }
}
