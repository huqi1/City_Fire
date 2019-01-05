package com.hq.modules.operate.controller;

import com.hq.common.utils.PageUtils;
import com.hq.common.utils.R;
import com.hq.common.validator.ValidatorUtils;
import com.hq.modules.operate.entity.EquipmentEntity;
import com.hq.modules.operate.service.EquipmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;






@RestController
@RequestMapping("equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:cfequipment:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = equipmentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{equipmentId}")
    @RequiresPermissions("sys:cfequipment:info")
    public R info(@PathVariable("equipmentId") String equipmentId){
        EquipmentEntity cfEquipment = equipmentService.selectById(equipmentId);

        return R.ok().put("cfEquipment", cfEquipment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:cfequipment:save")
    public R save(@RequestBody EquipmentEntity cfEquipment){
        equipmentService.insert(cfEquipment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:cfequipment:update")
    public R update(@RequestBody EquipmentEntity cfEquipment){
        ValidatorUtils.validateEntity(cfEquipment);
        equipmentService.updateAllColumnById(cfEquipment);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:cfequipment:delete")
    public R delete(@RequestBody String[] equipmentIds){
        equipmentService.deleteBatchIds(Arrays.asList(equipmentIds));

        return R.ok();
    }

}
