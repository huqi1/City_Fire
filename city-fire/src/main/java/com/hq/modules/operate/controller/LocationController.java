package com.hq.modules.operate.controller;

import com.hq.common.utils.DateUtils;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.R;
import com.hq.modules.operate.entity.LocationEntity;
import com.hq.modules.operate.service.LocationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;




/**
 *
 *
 * @author hq
 * @date 2018-12-17 09:25:46
 */
@RestController
@RequestMapping("location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operate:location:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = locationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{locationId}")
    @RequiresPermissions("operate:location:info")
    public R info(@PathVariable("locationId") String locationId){
        LocationEntity cfLocation = locationService.selectById(locationId);

        return R.ok().put("cfLocation", cfLocation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operate:location:save")
    public R save(@RequestBody LocationEntity cfLocation){
        int lat = cfLocation.getLat().intValue();
        int lng = cfLocation.getLng().intValue();
        cfLocation.setLocationId(cfLocation.getCitycode()+cfLocation.getAdcode()+lat+""+lng+DateUtils.getHHmmssTime());
        cfLocation.setStatus(0);
        cfLocation.setGmtCreate(new Date());
        locationService.insertAllColumn(cfLocation);
        return R.ok();
    }

    /**
     * 修改备注
     */
    @RequestMapping("/updateRemark")
    @RequiresPermissions("operate:location:updateRemark")
    public R updateRemark(@RequestParam Map<String,Object> params){
        String locationid = params.get("locationid").toString();
        String remark = " ";
        if(params.get("remark") == null || params.get("remark").equals("")){
        }else remark = params.get("remark").toString();
        locationService.updateRemarkByid(locationid,remark);
        return R.ok();
    }

    /**
     * 修改状态
     */
    @RequestMapping("/updateStatus")
    @RequiresPermissions("operate:location:updateStatus")
    public R updateStatus(@RequestParam Map<String,Object> params){
        String locationid = params.get("locationid").toString();
        String status = params.get("status").toString();
        locationService.updateStatusByid(locationid,status);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operate:location:delete")
    public R delete(@RequestBody String[] locationIds){
        locationService.deleteBatchIds(Arrays.asList(locationIds));
        return R.ok();
    }

}
