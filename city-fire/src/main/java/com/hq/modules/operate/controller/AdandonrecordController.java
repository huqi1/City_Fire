package com.hq.modules.operate.controller;

import com.hq.common.utils.PageUtils;
import com.hq.common.utils.R;
import com.hq.common.validator.ValidatorUtils;
import com.hq.modules.operate.entity.AdandonrecordEntity;
import com.hq.modules.operate.service.AdandonrecordService;
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
@RequestMapping("sys/adandonrecord")
public class AdandonrecordController {
    @Autowired
    private AdandonrecordService adandonrecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:cfadandonrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = adandonrecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{recordId}")
    @RequiresPermissions("sys:cfadandonrecord:info")
    public R info(@PathVariable("recordId") String recordId){
        AdandonrecordEntity cfAdandonrecord = adandonrecordService.selectById(recordId);

        return R.ok().put("cfAdandonrecord", cfAdandonrecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:cfadandonrecord:save")
    public R save(@RequestBody AdandonrecordEntity cfAdandonrecord){
        adandonrecordService.insert(cfAdandonrecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:cfadandonrecord:update")
    public R update(@RequestBody AdandonrecordEntity cfAdandonrecord){
        ValidatorUtils.validateEntity(cfAdandonrecord);
        adandonrecordService.updateAllColumnById(cfAdandonrecord);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:cfadandonrecord:delete")
    public R delete(@RequestBody String[] recordIds){
        adandonrecordService.deleteBatchIds(Arrays.asList(recordIds));

        return R.ok();
    }

}
