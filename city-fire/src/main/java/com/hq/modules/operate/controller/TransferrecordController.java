package com.hq.modules.operate.controller;

import com.hq.common.utils.PageUtils;
import com.hq.common.utils.R;
import com.hq.common.validator.ValidatorUtils;
import com.hq.modules.operate.entity.TransferrecordEntity;
import com.hq.modules.operate.service.TransferrecordService;
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
@RequestMapping("sys/transferrecord")
public class TransferrecordController {
    @Autowired
    private TransferrecordService transferrecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:cftransferrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = transferrecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{recordId}")
    @RequiresPermissions("sys:cftransferrecord:info")
    public R info(@PathVariable("recordId") String recordId){
        TransferrecordEntity cfTransferrecord = transferrecordService.selectById(recordId);

        return R.ok().put("cfTransferrecord", cfTransferrecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:cftransferrecord:save")
    public R save(@RequestBody TransferrecordEntity cfTransferrecord){
        transferrecordService.insert(cfTransferrecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:cftransferrecord:update")
    public R update(@RequestBody TransferrecordEntity cfTransferrecord){
        ValidatorUtils.validateEntity(cfTransferrecord);
        transferrecordService.updateAllColumnById(cfTransferrecord);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:cftransferrecord:delete")
    public R delete(@RequestBody String[] recordIds){
        transferrecordService.deleteBatchIds(Arrays.asList(recordIds));

        return R.ok();
    }

}
