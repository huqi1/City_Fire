package com.hq.modules.operate.controller;

import com.hq.common.utils.PageUtils;
import com.hq.common.utils.R;
import com.hq.common.validator.ValidatorUtils;
import com.hq.modules.operate.entity.WarningrecordEntity;
import com.hq.modules.operate.service.CfWarningrecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;






@RestController
@RequestMapping("sys/cfwarningrecord")
public class WarningrecordController {
    @Autowired
    private CfWarningrecordService cfWarningrecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:cfwarningrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cfWarningrecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{recordId}")
    @RequiresPermissions("sys:cfwarningrecord:info")
    public R info(@PathVariable("recordId") String recordId){
        WarningrecordEntity cfWarningrecord = cfWarningrecordService.selectById(recordId);

        return R.ok().put("cfWarningrecord", cfWarningrecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:cfwarningrecord:save")
    public R save(@RequestBody WarningrecordEntity cfWarningrecord){
        cfWarningrecordService.insert(cfWarningrecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:cfwarningrecord:update")
    public R update(@RequestBody WarningrecordEntity cfWarningrecord){
        ValidatorUtils.validateEntity(cfWarningrecord);
        cfWarningrecordService.updateAllColumnById(cfWarningrecord);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:cfwarningrecord:delete")
    public R delete(@RequestBody String[] recordIds){
        cfWarningrecordService.deleteBatchIds(Arrays.asList(recordIds));

        return R.ok();
    }

}
