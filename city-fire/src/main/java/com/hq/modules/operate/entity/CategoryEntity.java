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
@TableName("cf_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long categoryId;
	/**
	 * 分类名
	 */
	private String typeName;
	/**
	 * 分类编码
	 */
	private String typeId;
	/**
	 * 父级分类ID
	 */
	private String typePid;
	/**

	 * 父级分类ID
	 */
	private String typePname;
	/**
	 * 层级
	 */
	private String typeLevel;
	/**
	 * 状态 0:正常 1：删除 2：禁用
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 设置：
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取：
	 */
	public Long getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置：分类名
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取：分类名
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置：分类编码
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	/**
	 * 获取：分类编码
	 */
	public String getTypeId() {
		return typeId;
	}
	/**
	 * 设置：父级分类ID
	 */
	public void setTypePid(String typePid) {
		this.typePid = typePid;
	}
	/**
	 * 获取：父级分类ID
	 */
	public String getTypePid() {
		return typePid;
	}
	/**
	 * 设置：层级
	 */
	public void setTypeLevel(String typeLevel) {
		this.typeLevel = typeLevel;
	}
	/**
	 * 获取：层级
	 */
	public String getTypeLevel() {
		return typeLevel;
	}
	/**
	 * 设置：状态 0:正常 1：删除 2：禁用
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态 0:正常 1：删除 2：禁用
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

	public String getTypePname() {
		return typePname;
	}

	public void setTypePname(String typePname) {
		this.typePname = typePname;
	}
}
