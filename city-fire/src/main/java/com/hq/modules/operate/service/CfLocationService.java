package com.hq.modules.operate.service;

import com.baomidou.mybatisplus.service.IService;
import com.hq.common.utils.PageUtils;
import com.hq.modules.operate.entity.LocationEntity;

import java.util.Map;

/**
 *
 *
 * @author hq
 * @date 2018-12-17 09:25:46
 */
public interface CfLocationService extends IService<LocationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

