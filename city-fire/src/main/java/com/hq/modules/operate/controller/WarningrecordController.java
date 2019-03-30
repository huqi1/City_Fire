package com.hq.modules.operate.controller;

import com.hq.common.utils.PageUtils;
import com.hq.common.utils.R;
import com.hq.common.validator.ValidatorUtils;
import com.hq.modules.operate.entity.WarningrecordEntity;
import com.hq.modules.operate.service.EquipmentService;
import com.hq.modules.operate.service.WarningrecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;






@RestController
@RequestMapping("/report")
public class WarningrecordController {
    @Autowired
    private WarningrecordService warningrecordService;
    @Autowired
    private EquipmentService equipmentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operate:warningrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = warningrecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{recordId}")
    @RequiresPermissions("operate:warningrecord:info")
    public R info(@PathVariable("recordId") String recordId){
        WarningrecordEntity cfWarningrecord = warningrecordService.selectById(recordId);

        return R.ok().put("cfWarningrecord", cfWarningrecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operate:warningrecord:save")
    public R save(@RequestBody WarningrecordEntity cfWarningrecord){
        cfWarningrecord.setWarningTime(new Date());
        warningrecordService.insert(cfWarningrecord);
        equipmentService.updateStatusByid(cfWarningrecord.getOperatorStatus(),cfWarningrecord.getEquipmentId());
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operate:warningrecord:update")
    public R update(@RequestBody WarningrecordEntity cfWarningrecord){
        ValidatorUtils.validateEntity(cfWarningrecord);
        warningrecordService.updateAllColumnById(cfWarningrecord);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operate:warningrecord:delete")
    public R delete(@RequestBody String[] recordIds){
        warningrecordService.deleteBatchIds(Arrays.asList(recordIds));

        return R.ok();
    }

}
