package com.hq.modules.operate.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.Query;
import com.hq.modules.operate.dao.CategoryDao;
import com.hq.modules.operate.entity.CategoryEntity;
import com.hq.modules.operate.service.CfCategoryService;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("cfCategoryService")
public class CfCategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CfCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CategoryEntity> page = this.selectPage(
                new Query<CategoryEntity>(params).getPage(),
                new EntityWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

}
