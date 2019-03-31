package com.hq.modules.operate.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.Query;
import com.hq.modules.operate.dao.WarningrecordDao;
import com.hq.modules.operate.entity.WarningrecordEntity;
import com.hq.modules.operate.service.WarningrecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("WarningrecordService")
public class WarningrecordServiceImpl extends ServiceImpl<WarningrecordDao, WarningrecordEntity> implements WarningrecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String equipmentName = (String) params.get("equipmentName");
        String operatorStatus = (String) params.get("operatorStatus");
        String status = (String) params.get("status");
        Page<WarningrecordEntity> page = this.selectPage(
                new Query<WarningrecordEntity>(params).getPage(),
                new EntityWrapper<WarningrecordEntity>().where("1 = 1")
                            .and(StringUtils.isNotBlank(operatorStatus),"operator_status = {0}",operatorStatus)
                         .and(StringUtils.isNotBlank(status),"status = {0}",status)
                        .like(StringUtils.isNotBlank(equipmentName),"equipment_name",equipmentName)
            );

        return new PageUtils(page);
    }

    @Override
    public void dealWarning(WarningrecordEntity warningrecordEntity) {
        baseMapper.dealWarning(warningrecordEntity);
    }

}
