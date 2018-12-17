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
@TableName("cf_transferrecord")
public class TransferrecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String recordId;
	/**
	 * 流转设备id
	 */
	private String equipmentId;
	/**
	 * 流转设备名称
	 */
	private String equipmentName;
	/**
	 * 流转类型名称 0：安装 1：更换
	 */
	private Integer transferName;
	/**
	 * 原地址
	 */
	private String originalAddress;
	/**
	 * 目的地址
	 */
	private String destAddress;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 操作时间
	 */
	private Date operatTime;
	/**
	 * 操作原因
	 */
	private String operatReason;

	/**
	 * 设置：
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	/**
	 * 获取：
	 */
	public String getRecordId() {
		return recordId;
	}
	/**
	 * 设置：流转设备id
	 */
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	/**
	 * 获取：流转设备id
	 */
	public String getEquipmentId() {
		return equipmentId;
	}
	/**
	 * 设置：流转设备名称
	 */
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	/**
	 * 获取：流转设备名称
	 */
	public String getEquipmentName() {
		return equipmentName;
	}
	/**
	 * 设置：流转类型名称 0：安装 1：更换
	 */
	public void setTransferName(Integer transferName) {
		this.transferName = transferName;
	}
	/**
	 * 获取：流转类型名称 0：安装 1：更换
	 */
	public Integer getTransferName() {
		return transferName;
	}
	/**
	 * 设置：原地址
	 */
	public void setOriginalAddress(String originalAddress) {
		this.originalAddress = originalAddress;
	}
	/**
	 * 获取：原地址
	 */
	public String getOriginalAddress() {
		return originalAddress;
	}
	/**
	 * 设置：目的地址
	 */
	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}
	/**
	 * 获取：目的地址
	 */
	public String getDestAddress() {
		return destAddress;
	}
	/**
	 * 设置：操作人
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * 获取：操作人
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * 设置：操作时间
	 */
	public void setOperatTime(Date operatTime) {
		this.operatTime = operatTime;
	}
	/**
	 * 获取：操作时间
	 */
	public Date getOperatTime() {
		return operatTime;
	}
	/**
	 * 设置：操作原因
	 */
	public void setOperatReason(String operatReason) {
		this.operatReason = operatReason;
	}
	/**
	 * 获取：操作原因
	 */
	public String getOperatReason() {
		return operatReason;
	}
}
