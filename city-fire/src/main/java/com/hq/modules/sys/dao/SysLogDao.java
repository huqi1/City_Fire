

package com.hq.modules.sys.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hq.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}
