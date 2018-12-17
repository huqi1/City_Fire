package com.hq.modules.operate.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.Query;
import com.hq.modules.operate.dao.TransferrecordDao;
import com.hq.modules.operate.entity.TransferrecordEntity;
import com.hq.modules.operate.service.CfTransferrecordService;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("cfTransferrecordService")
public class CfTransferrecordServiceImpl extends ServiceImpl<TransferrecordDao, TransferrecordEntity> implements CfTransferrecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TransferrecordEntity> page = this.selectPage(
                new Query<TransferrecordEntity>(params).getPage(),
                new EntityWrapper<TransferrecordEntity>()
        );

        return new PageUtils(page);
    }

}
