

package com.hq.modules.job.service;

import com.baomidou.mybatisplus.service.IService;
import com.hq.common.utils.PageUtils;
import com.hq.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;


/**
 * 定时任务日志
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {
	PageUtils queryPage(Map<String, Object> params);
	
}
