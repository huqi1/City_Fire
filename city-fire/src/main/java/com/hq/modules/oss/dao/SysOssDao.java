

package com.hq.modules.oss.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hq.modules.oss.entity.SysOssEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传
 */
@Mapper
public interface SysOssDao extends BaseMapper<SysOssEntity> {
	
}
