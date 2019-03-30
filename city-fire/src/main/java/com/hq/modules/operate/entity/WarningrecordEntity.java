package com.hq.modules.operate.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author hq
 * @date 2018-12-17 09:25:46
 */
@TableName("cf_warningrecord")
public class WarningrecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long recordId;
	/**
	 * 报警设备id
	 */
	private String equipmentId;
	/**
	 * 报警设备名称
	 */
	private String equipmentName;
	/**
	 * 报警人
	 */
	private String warningOperator;
	/**
	 * 操作项
	 * 0 ：正常
	 * 1 ：报修
	 * 2 ：报废
	 */
	private int operatorStatus;
	/**
	 * 报警时间
	 */
	private Date warningTime;
	/**
	 * 报警原因
	 */
	private String warningReason;
	/**
	 * 处理人
	 */
	private String dealOperator;
	/**
	 * 处理时间
	 */
	private Date dealTime;
	/**
	 * 处理方案
	 */
	private String dealPlan;
	/**
	 * 当前状态 0：解决 1：未解决
	 */
	private Integer status;

	/**
	 * 设置：
	 */
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	/**
	 * 获取：
	 */
	public Long getRecordId() {
		return recordId;
	}
	/**
	 * 设置：报警设备id
	 */
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	/**
	 * 获取：报警设备id
	 */
	public String getEquipmentId() {
		return equipmentId;
	}
	/**
	 * 设置：报警设备名称
	 */
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	/**
	 * 获取：报警设备名称
	 */
	public String getEquipmentName() {
		return equipmentName;
	}
	/**
	 * 设置：报警人
	 */
	public void setWarningOperator(String warningOperator) {
		this.warningOperator = warningOperator;
	}
	/**
	 * 获取：报警人
	 */
	public String getWarningOperator() {
		return warningOperator;
	}
	/**
	 * 设置：报警时间
	 */
	public void setWarningTime(Date warningTime) {
		this.warningTime = warningTime;
	}
	/**
	 * 获取：报警时间
	 */
	public Date getWarningTime() {
		return warningTime;
	}
	/**
	 * 设置：报警原因
	 */
	public void setWarningReason(String warningReason) {
		this.warningReason = warningReason;
	}
	/**
	 * 获取：报警原因
	 */
	public String getWarningReason() {
		return warningReason;
	}
	/**
	 * 设置：处理人
	 */
	public void setDealOperator(String dealOperator) {
		this.dealOperator = dealOperator;
	}
	/**
	 * 获取：处理人
	 */
	public String getDealOperator() {
		return dealOperator;
	}
	/**
	 * 设置：处理时间
	 */
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	/**
	 * 获取：处理时间
	 */
	public Date getDealTime() {
		return dealTime;
	}
	/**
	 * 设置：处理方案
	 */
	public void setDealPlan(String dealPlan) {
		this.dealPlan = dealPlan;
	}
	/**
	 * 获取：处理方案
	 */
	public String getDealPlan() {
		return dealPlan;
	}
	/**
	 * 设置：当前状态 0：解决 1：未解决
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：当前状态 0：解决 1：未解决
	 */
	public Integer getStatus() {
		return status;
	}

	public int getOperatorStatus() {
		return operatorStatus;
	}

	public void setOperatorStatus(int operatorStatus) {
		this.operatorStatus = operatorStatus;
	}
}
