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
@TableName("cf_equipment")
public class EquipmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String equipmentId;
	/**
	 * 设备名称
	 */
	private String equipmentName;
	/**
	 * 所属分类id
	 */
	private String belongTypeid;
	/**
	 * 所属分类名称
	 */
	private String belongTypename;
	/**
	 * 设备价格
	 */
	private Integer equipmentPrice;
	/**
	 * 设备数量
	 */
	private Integer equipmentNum;
	/**
	 * 设备所在位置id
	 */
	private String localtionId;
	/**
	 * 设备所在位置名称
	 */
	private String locationName;
	/**
	 * 设备状态0:正常 1：报修 2：检修 3：报废
	 */
	private Integer equipmentStatus;
	/**
	 * 设备状态: 0:在库 1：检修 2：在用
	 */
	private Integer locationStatus;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 设置：
	 */
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	/**
	 * 获取：
	 */
	public String getEquipmentId() {
		return equipmentId;
	}
	/**
	 * 设置：设备名称
	 */
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	/**
	 * 获取：设备名称
	 */
	public String getEquipmentName() {
		return equipmentName;
	}
	/**
	 * 设置：所属分类id
	 */
	public void setBelongTypeid(String belongTypeid) {
		this.belongTypeid = belongTypeid;
	}
	/**
	 * 获取：所属分类id
	 */
	public String getBelongTypeid() {
		return belongTypeid;
	}
	/**
	 * 设置：所属分类名称
	 */
	public void setBelongTypename(String belongTypename) {
		this.belongTypename = belongTypename;
	}
	/**
	 * 获取：所属分类名称
	 */
	public String getBelongTypename() {
		return belongTypename;
	}
	/**
	 * 设置：设备价格
	 */
	public void setEquipmentPrice(Integer equipmentPrice) {
		this.equipmentPrice = equipmentPrice;
	}
	/**
	 * 获取：设备价格
	 */
	public Integer getEquipmentPrice() {
		return equipmentPrice;
	}
	/**
	 * 设置：设备数量
	 */
	public void setEquipmentNum(Integer equipmentNum) {
		this.equipmentNum = equipmentNum;
	}
	/**
	 * 获取：设备数量
	 */
	public Integer getEquipmentNum() {
		return equipmentNum;
	}
	/**
	 * 设置：设备所在位置id
	 */
	public void setLocaltionId(String localtionId) {
		this.localtionId = localtionId;
	}
	/**
	 * 获取：设备所在位置id
	 */
	public String getLocaltionId() {
		return localtionId;
	}
	/**
	 * 设置：设备所在位置名称
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	/**
	 * 获取：设备所在位置名称
	 */
	public String getLocationName() {
		return locationName;
	}
	/**
	 * 设置：设备状态0:正常 1：报修 2：检修 3：报废
	 */
	public void setEquipmentStatus(Integer equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}
	/**
	 * 获取：设备状态0:正常 1：报修 2：检修 3：报废
	 */
	public Integer getEquipmentStatus() {
		return equipmentStatus;
	}
	/**
	 * 设置：设备状态: 0:在库 1：检修 2：在用
	 */
	public void setLocationStatus(Integer locationStatus) {
		this.locationStatus = locationStatus;
	}
	/**
	 * 获取：设备状态: 0:在库 1：检修 2：在用
	 */
	public Integer getLocationStatus() {
		return locationStatus;
	}
	/**
	 * 设置：创建时间
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}
}
