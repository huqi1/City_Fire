package com.hq.modules.operate.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hq.modules.operate.entity.LocationEntity;
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
public interface LocationDao extends BaseMapper<LocationEntity> {

    public void updateRemarkByid(@Param("locationid") String locationid,@Param("remark") String remark);

    public void updateStatusByid(@Param("locationid") String locationid,@Param("status") String status);

    /**
     *
     * 根据市(citycode)查询所有区*/
    public List<String> selectDistrictBycityCode(@Param("citycode") String citycode);

    /**
     *根据市(citycode)查询所有区
     * */
    public List<LocationEntity> selectBydistrict(@Param("district") String citycode);
}
