package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.HsdConnector;
import io.renren.modules.sys.entity.SysDictEntity;
import io.renren.modules.sys.service.HsdConnectorServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @description: 接口管理
 * @author: zh
 * @create: 2019-12-24 10:38
 **/
@RestController
@RequestMapping("/sys/hsdConnector")
public class HsdConnectorController {
    @Autowired
    private HsdConnectorServcie hsdConnectorServcie;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hsdConnectorServcie.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        HsdConnector hsdConnector = hsdConnectorServcie.getById(id);

        return R.ok().put("hsdConnector", hsdConnector);
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody HsdConnector hsdConnector){
        //校验类型
        //ValidatorUtils.validateEntity(dict);

        hsdConnectorServcie.save(hsdConnector);

        return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody HsdConnector hsdConnector){
        //校验类型
        //ValidatorUtils.validateEntity(dict);

        hsdConnectorServcie.updateById(hsdConnector);

        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        hsdConnectorServcie.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
