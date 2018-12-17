

package com.hq.modules.job.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hq.modules.job.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 */
@Mapper
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLogEntity> {
	
}
