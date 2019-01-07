package com.hq.modules.operate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.R;
import com.hq.common.validator.ValidatorUtils;
import com.hq.modules.operate.entity.EquipmentEntity;
import com.hq.modules.operate.service.EquipmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
    //@RequiresPermissions("operate:equipment:list")
    public R list(@RequestParam Map<String, Object> params){
        params.put("sidx","gmt_create");
        params.put("order","DESC");
        PageUtils page = equipmentService.queryPage(params);

        Map<String,Object> map = new HashMap<>();
        map.put("page", page);
        return R.ok(map);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{equipmentId}")
    @RequiresPermissions("operate:equipment:info")
    public R info(@PathVariable("equipmentId") String equipmentId){
        EquipmentEntity cfEquipment = equipmentService.selectById(equipmentId);

        return R.ok().put("cfEquipment", cfEquipment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operate:equipment:save")
    public R save(@RequestBody EquipmentEntity cfEquipment){
        cfEquipment.setEquipmentStatus(0);
        cfEquipment.setLocationStatus(0);
        cfEquipment.setGmtCreate(new Date());
        equipmentService.insertAllColumn(cfEquipment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operate:equipment:update")
    public R update(@RequestBody EquipmentEntity cfEquipment){
        ValidatorUtils.validateEntity(cfEquipment);
        equipmentService.updateAllColumnById(cfEquipment);//全部更新
        
        return R.ok();
    }

    /**
     * 修改by oldid
     */
    @RequestMapping("/updatebyid")
    @RequiresPermissions("operate:equipment:update")
    public R update(@RequestParam Map<String,Object> map){
        EquipmentEntity equipment  = JSON.parseObject((String) map.get("equipment"),new TypeReference<EquipmentEntity>(){});
        String id = (String) map.get("id");
        //ValidatorUtils.validateEntity(cfEquipment);
        equipmentService.updateAllByid(equipment,id);//全部更新
        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operate:equipment:delete")
    public R delete(@RequestBody String[] equipmentIds){
        equipmentService.deleteBatchIds(Arrays.asList(equipmentIds));

        return R.ok();
    }

}
