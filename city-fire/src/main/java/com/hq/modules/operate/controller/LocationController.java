package com.hq.modules.operate.controller;

import com.hq.common.utils.PageUtils;
import com.hq.common.utils.R;
import com.hq.common.validator.ValidatorUtils;
import com.hq.modules.operate.entity.LocationEntity;
import com.hq.modules.operate.service.LocationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;




/**
 *
 *
 * @author hq
 * @date 2018-12-17 09:25:46
 */
@RestController
@RequestMapping("sys/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:cflocation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = locationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{locationId}")
    @RequiresPermissions("sys:cflocation:info")
    public R info(@PathVariable("locationId") String locationId){
        LocationEntity cfLocation = locationService.selectById(locationId);

        return R.ok().put("cfLocation", cfLocation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:cflocation:save")
    public R save(@RequestBody LocationEntity cfLocation){
        locationService.insert(cfLocation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:cflocation:update")
    public R update(@RequestBody LocationEntity cfLocation){
        ValidatorUtils.validateEntity(cfLocation);
        locationService.updateAllColumnById(cfLocation);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:cflocation:delete")
    public R delete(@RequestBody String[] locationIds){
        locationService.deleteBatchIds(Arrays.asList(locationIds));

        return R.ok();
    }

}
