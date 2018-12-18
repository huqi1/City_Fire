package com.hq.modules.operate.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hq.modules.operate.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 *
 * @author hq
 * @date 2018-12-17 09:25:46
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

    /**
     *
     *
     * 通过pid修改对应子菜单的pname
     * @params：Pid ,Pname
     */
    void changPnameBypid(@Param("Pid") String Pid, @Param("Pname")String Pname);
}
