package com.hq.modules.operate.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hq.modules.operate.entity.EquipmentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 * @author hq
 * @date 2018-12-17 09:25:46
 */
@Mapper
public interface EquipmentDao extends BaseMapper<EquipmentEntity> {

    public void updateAllByid(@Param("equipment") EquipmentEntity equipmentEntity,@Param("id") String id);

    List<String> selectAllType();

    List<String> selectAllCommunity();
}
