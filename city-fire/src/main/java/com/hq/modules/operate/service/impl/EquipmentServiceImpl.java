package com.hq.modules.operate.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.Query;
import com.hq.modules.operate.dao.EquipmentDao;
import com.hq.modules.operate.entity.EquipmentEntity;
import com.hq.modules.operate.service.EquipmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("EquipmentService")
public class EquipmentServiceImpl extends ServiceImpl<EquipmentDao, EquipmentEntity> implements EquipmentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EquipmentEntity> page = this.selectPage(
                new Query<EquipmentEntity>(params).getPage(),
                new EntityWrapper<EquipmentEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void updateAllByid(EquipmentEntity equipmentEntity, String id) {
       baseMapper.updateAllByid(equipmentEntity,id);
    }

    @Override
    public List<String> selectAllType() {
        return baseMapper.selectAllType();
    }

    @Override
    public List<String> selectAllCommunity() {
        return baseMapper.selectAllCommunity();
    }

}
