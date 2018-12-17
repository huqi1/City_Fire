package com.hq.modules.operate.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.Query;
import com.hq.modules.operate.dao.AdandonrecordDao;
import com.hq.modules.operate.entity.AdandonrecordEntity;
import com.hq.modules.operate.service.CfAdandonrecordService;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("cfAdandonrecordService")
public class CfAdandonrecordServiceImpl extends ServiceImpl<AdandonrecordDao, AdandonrecordEntity> implements CfAdandonrecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<AdandonrecordEntity> page = this.selectPage(
                new Query<AdandonrecordEntity>(params).getPage(),
                new EntityWrapper<AdandonrecordEntity>()
        );

        return new PageUtils(page);
    }

}
