package com.hq.modules.operate.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hq.modules.operate.entity.LocationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 *
 * @author hq
 * @date 2018-12-17 09:25:46
 */
@Mapper
public interface LocationDao extends BaseMapper<LocationEntity> {

    public void updateRemarkByid(@Param("locationid") String locationid,@Param("remark") String remark);

    public void updateStatusByid(@Param("locationid") String locationid,@Param("status") String status);

}
