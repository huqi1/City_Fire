

package com.hq.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hq.modules.sys.entity.SysCaptchaEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码
 */
@Mapper
public interface SysCaptchaDao extends BaseMapper<SysCaptchaEntity> {

}
