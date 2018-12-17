

package com.hq.modules.oss.service;

import com.baomidou.mybatisplus.service.IService;
import com.hq.common.utils.PageUtils;
import com.hq.modules.oss.entity.SysOssEntity;

import java.util.Map;

/**
 * 文件上传
 */
public interface SysOssService extends IService<SysOssEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
