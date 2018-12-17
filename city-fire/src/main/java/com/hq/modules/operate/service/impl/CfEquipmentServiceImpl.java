package com.hq.modules.operate.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.Query;
import com.hq.modules.operate.dao.EquipmentDao;
import com.hq.modules.operate.entity.EquipmentEntity;
import com.hq.modules.operate.service.CfEquipmentService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cfEquipmentService")
public class CfEquipmentServiceImpl extends ServiceImpl<EquipmentDao, EquipmentEntity> implements CfEquipmentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EquipmentEntity> page = this.selectPage(
                new Query<EquipmentEntity>(params).getPage(),
                new EntityWrapper<EquipmentEntity>()
        );

        return new PageUtils(page);
    }

}
