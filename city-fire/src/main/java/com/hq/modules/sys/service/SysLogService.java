
package com.hq.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.hq.common.utils.PageUtils;
import com.hq.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
