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

        //分类列表不参与分页
        params.put("page","1");
        params.put("limit","1000");
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
        cfCategory.setStatus(0);
        cfCategory.setGmtCreate(new Date());
        categoryService.updateAllColumnById(cfCategory);//全部更新
        // if 名称不为null，级联更新子菜单的Pname
        if (cfCategory.getTypeName() != "" && !cfCategory.getTypeName().equals("")) {
            categoryService.changPnameBypid(cfCategory.getTypeId(), cfCategory.getTypeName());
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operate:category:delete")
    public R delete(@RequestParam Map<String, Object> params){
        Long categoryId = Long.valueOf(params.get("categoryId").toString());
        categoryService.deleteById(categoryId);
        return R.ok();
    }

}
