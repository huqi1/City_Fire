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
@TableName("cf_location")
public class LocationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String locationId;
	/**
	 * 位置全称名
	 */
	private String locationName;
	/**
	 * 归宿地id
	 */
	private String belongAreaid;
	/**
	 * 归属地名称
	 */
	private String belongAreaname;
	/**
	 * 设备状态: 0:正常 1：删除 2：禁用
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 设置：
	 */
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	/**
	 * 获取：
	 */
	public String getLocationId() {
		return locationId;
	}
	/**
	 * 设置：位置全称名
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	/**
	 * 获取：位置全称名
	 */
	public String getLocationName() {
		return locationName;
	}
	/**
	 * 设置：归宿地id
	 */
	public void setBelongAreaid(String belongAreaid) {
		this.belongAreaid = belongAreaid;
	}
	/**
	 * 获取：归宿地id
	 */
	public String getBelongAreaid() {
		return belongAreaid;
	}
	/**
	 * 设置：归属地名称
	 */
	public void setBelongAreaname(String belongAreaname) {
		this.belongAreaname = belongAreaname;
	}
	/**
	 * 获取：归属地名称
	 */
	public String getBelongAreaname() {
		return belongAreaname;
	}
	/**
	 * 设置：设备状态: 0:正常 1：删除 2：禁用
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：设备状态: 0:正常 1：删除 2：禁用
	 */
	public Integer getStatus() {
		return status;
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
