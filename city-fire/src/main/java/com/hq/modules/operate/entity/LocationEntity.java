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
@TableName("cf_location")
public class LocationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "location_id",type = IdType.INPUT)
	private String locationId;
	/**
	 * 位置全称名
	 */
	private String locationName;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 市级编码
	 */
	private String citycode;
	/**
	 * 区
	 */
	private String district;
	/**
	 * 区级编码
	 */
	private String adcode;
	/**
	 * 街道
	 */
	private String street;
	/**
	 * 街道编号
	 */
	private String streetNumber;
	/**
	 * 经度
	 */
	private Double lat;
	/**
	 * 纬度
	 */
	private Double lng;

	/**
	 * 设备状态: 0:正常 1：删除 2：禁用
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAdcode() {
		return adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
