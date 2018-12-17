package com.hq.modules.operate.controller;

import com.hq.common.utils.PageUtils;
import com.hq.common.utils.R;
import com.hq.common.validator.ValidatorUtils;
import com.hq.modules.operate.entity.CategoryEntity;
import com.hq.modules.operate.service.CfCategoryService;
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
@RequestMapping("sys/cfcategory")
public class CategoryController {
    @Autowired
    private CfCategoryService cfCategoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:cfcategory:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cfCategoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{categoryId}")
    @RequiresPermissions("sys:cfcategory:info")
    public R info(@PathVariable("categoryId") String categoryId){
        CategoryEntity cfCategory = cfCategoryService.selectById(categoryId);

        return R.ok().put("cfCategory", cfCategory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:cfcategory:save")
    public R save(@RequestBody CategoryEntity cfCategory){
        cfCategoryService.insert(cfCategory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:cfcategory:update")
    public R update(@RequestBody CategoryEntity cfCategory){
        ValidatorUtils.validateEntity(cfCategory);
        cfCategoryService.updateAllColumnById(cfCategory);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:cfcategory:delete")
    public R delete(@RequestBody String[] categoryIds){
        cfCategoryService.deleteBatchIds(Arrays.asList(categoryIds));

        return R.ok();
    }

}
