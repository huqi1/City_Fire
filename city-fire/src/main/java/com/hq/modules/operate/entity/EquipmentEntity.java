package com.hq.modules.operate.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

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
	@TableId(value = "equipment_id",type = IdType.INPUT)
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
	 * 设备状态0:正常 1：报修 2：报废
	 */
	private Integer equipmentStatus;
	/**
	 * 设备状态: 0:在库 1：检修 2：在用
	 */
	private Integer locationStatus;

	/**
	 * 小区
	 */
	private String community;
	/**
	 * 单元
	 */
	private String unit;
	/**
	 * 楼层
	 */
	private String floor;
	/**
	 * 楼道
	 */
	private String corridor;
	/**
	 * 房间号
	 */
	private String roomNumber;
	/**
	 * 管理员
	 */
	private String administrator;
	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getBelongTypeid() {
		return belongTypeid;
	}

	public void setBelongTypeid(String belongTypeid) {
		this.belongTypeid = belongTypeid;
	}

	public String getBelongTypename() {
		return belongTypename;
	}

	public void setBelongTypename(String belongTypename) {
		this.belongTypename = belongTypename;
	}

	public Integer getEquipmentPrice() {
		return equipmentPrice;
	}

	public void setEquipmentPrice(Integer equipmentPrice) {
		this.equipmentPrice = equipmentPrice;
	}

	public Integer getEquipmentNum() {
		return equipmentNum;
	}

	public void setEquipmentNum(Integer equipmentNum) {
		this.equipmentNum = equipmentNum;
	}

	public String getLocaltionId() {
		return localtionId;
	}

	public void setLocaltionId(String localtionId) {
		this.localtionId = localtionId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getEquipmentStatus() {
		return equipmentStatus;
	}

	public void setEquipmentStatus(Integer equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}

	public Integer getLocationStatus() {
		return locationStatus;
	}

	public void setLocationStatus(Integer locationStatus) {
		this.locationStatus = locationStatus;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getCorridor() {
		return corridor;
	}

	public void setCorridor(String corridor) {
		this.corridor = corridor;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getAdministrator() {
		return administrator;
	}

	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
}
