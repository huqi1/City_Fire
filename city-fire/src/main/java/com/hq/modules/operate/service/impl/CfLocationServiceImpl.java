package com.hq.modules.operate.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.Query;
import com.hq.modules.operate.dao.LocationDao;
import com.hq.modules.operate.entity.LocationEntity;
import com.hq.modules.operate.service.CfLocationService;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("cfLocationService")
public class CfLocationServiceImpl extends ServiceImpl<LocationDao, LocationEntity> implements CfLocationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<LocationEntity> page = this.selectPage(
                new Query<LocationEntity>(params).getPage(),
                new EntityWrapper<LocationEntity>()
        );

        return new PageUtils(page);
    }

}
