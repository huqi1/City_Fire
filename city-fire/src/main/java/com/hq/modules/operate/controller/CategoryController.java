package com.hq.modules.operate.controller;

import com.hq.common.utils.DateUtils;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.R;
import com.hq.common.validator.ValidatorUtils;
import com.hq.modules.operate.entity.CategoryEntity;
import com.hq.modules.operate.service.CategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;





/**
 *
 *
 * @author hq
 * @date 2018-12-17 09:25:46
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operate:category:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{categoryId}")
    @RequiresPermissions("operate:category:info")
    public R info(@PathVariable("categoryId") String categoryId){
        CategoryEntity cfCategory = categoryService.selectById(categoryId);

        return R.ok().put("cfCategory", cfCategory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operate:category:save")
    public R save(@RequestBody CategoryEntity cfCategory){
        Calendar calendar = Calendar.getInstance();
        cfCategory.setTypeId(cfCategory.getTypePid()+DateUtils.getHHmmssTime());
        cfCategory.setGmtCreate(new Date());
        categoryService.insert(cfCategory);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operate:category:update")
    public R update(@RequestBody CategoryEntity cfCategory){
        ValidatorUtils.validateEntity(cfCategory);
        categoryService.updateAllColumnById(cfCategory);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operate:category:delete")
    public R delete(@RequestBody String[] categoryIds){
        categoryService.deleteBatchIds(Arrays.asList(categoryIds));

        return R.ok();
    }

}
