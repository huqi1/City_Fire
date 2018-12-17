

package com.hq.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.hq.common.utils.PageUtils;
import com.hq.modules.sys.entity.SysConfigEntity;

import java.util.Map;

/**
 * 系统配置信息
 */
public interface SysConfigService extends IService<SysConfigEntity>  {

	PageUtils queryPage(Map<String, Object> params);
	
	/**
	 * 保存配置信息
	 */
    void save(SysConfigEntity config);
	
	/**
	 * 更新配置信息
	 */
    void update(SysConfigEntity config);
	
	/**
	 * 根据key，更新value
	 */
    void updateValueByKey(String key, String value);
	
	/**
	 * 删除配置信息
	 */
    void deleteBatch(Long[] ids);
	
	/**
	 * 根据key，获取配置的value值
	 * 
	 * @param key           key
	 */
    String getValue(String key);
	
	/**
	 * 根据key，获取value的Object对象
	 * @param key    key
	 * @param clazz  Object对象
	 */
    <T> T getConfigObject(String key, Class<T> clazz);
	
}
