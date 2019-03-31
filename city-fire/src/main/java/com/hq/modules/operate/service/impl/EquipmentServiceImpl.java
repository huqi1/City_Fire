package com.hq.modules.operate.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.Query;
import com.hq.modules.operate.dao.EquipmentDao;
import com.hq.modules.operate.entity.EquipmentEntity;
import com.hq.modules.operate.service.EquipmentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("EquipmentService")
public class EquipmentServiceImpl extends ServiceImpl<EquipmentDao, EquipmentEntity> implements EquipmentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String equipmentName = (String) params.get("equipmentName");
        String belongTypeName = (String) params.get("belongTypeName");
        String community = (String) params.get("community");
        Page<EquipmentEntity> page = this.selectPage(
                new Query<EquipmentEntity>(params).getPage(),
                new EntityWrapper<EquipmentEntity>().where("1 = 1")
                        .and(StringUtils.isNotBlank(belongTypeName),"belong_typename = {0}",belongTypeName)
                .and(StringUtils.isNotBlank(community),"community = {0}",community)
                .like(StringUtils.isNotBlank(equipmentName),"equipment_name ",equipmentName)
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

    @Override
    public void updateStatusByid(int equipmentStatus, String id) {
        baseMapper.updateStatusByid(equipmentStatus,id);
    }

}
