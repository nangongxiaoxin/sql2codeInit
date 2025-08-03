package com.slilio.sql2codeInitDemo.entity.query;

import java.util.Date;
import java.math.BigDecimal;

/**
 * @Description: 产品信息查询
 *
 * @Author: slilio
 * @CreateTime: 2025-08-04
 */
public class ProductInfoQuery extends BaseQuery{
	/**
	 * 自增ID
	 */
	private Integer id;

	/**
	 * 厂商ID
	 */
	private String companyId;

	private String companyIdFuzzy;

	/**
	 * 编码
	 */
	private String code;

	private String codeFuzzy;

	/**
	 * 产品名
	 */
	private String productName;

	private String productNameFuzzy;

	/**
	 * 价格
	 */
	private BigDecimal price;

	/**
	 * 类型
	 */
	private Integer skuType;

	/**
	 * 颜色
	 */
	private Integer colorType;

	/**
	 * 创建时间
	 */
	private Date createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 创建日期
	 */
	private Date createDate;

	private String createDateStart;

	private String createDateEnd;

	/**
	 * 库存
	 */
	private Long stock;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 删除状态：0：删除；1：正常
	 */
	private Integer isDel;

	public void setId (Integer id) {
		this.id = id;
	}

	public Integer getId () {
		return this.id;
	}

	public void setCompanyId (String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyId () {
		return this.companyId;
	}

	public void setCode (String code) {
		this.code = code;
	}

	public String getCode () {
		return this.code;
	}

	public void setProductName (String productName) {
		this.productName = productName;
	}

	public String getProductName () {
		return this.productName;
	}

	public void setPrice (BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPrice () {
		return this.price;
	}

	public void setSkuType (Integer skuType) {
		this.skuType = skuType;
	}

	public Integer getSkuType () {
		return this.skuType;
	}

	public void setColorType (Integer colorType) {
		this.colorType = colorType;
	}

	public Integer getColorType () {
		return this.colorType;
	}

	public void setCreateTime (Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime () {
		return this.createTime;
	}

	public void setCreateDate (Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate () {
		return this.createDate;
	}

	public void setStock (Long stock) {
		this.stock = stock;
	}

	public Long getStock () {
		return this.stock;
	}

	public void setStatus (Integer status) {
		this.status = status;
	}

	public Integer getStatus () {
		return this.status;
	}

	public void setIsDel (Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsDel () {
		return this.isDel;
	}

	public void setCompanyIdFuzzy (String companyIdFuzzy) {
		this.companyIdFuzzy = companyIdFuzzy;
	}

	public String getCompanyIdFuzzy () {
		return this.companyIdFuzzy;
	}

	public void setCodeFuzzy (String codeFuzzy) {
		this.codeFuzzy = codeFuzzy;
	}

	public String getCodeFuzzy () {
		return this.codeFuzzy;
	}

	public void setProductNameFuzzy (String productNameFuzzy) {
		this.productNameFuzzy = productNameFuzzy;
	}

	public String getProductNameFuzzy () {
		return this.productNameFuzzy;
	}

	public void setCreateTimeStart (String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeStart () {
		return this.createTimeStart;
	}

	public void setCreateTimeEnd (String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getCreateTimeEnd () {
		return this.createTimeEnd;
	}

	public void setCreateDateStart (String createDateStart) {
		this.createDateStart = createDateStart;
	}

	public String getCreateDateStart () {
		return this.createDateStart;
	}

	public void setCreateDateEnd (String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public String getCreateDateEnd () {
		return this.createDateEnd;
	}


}