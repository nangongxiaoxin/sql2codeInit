package com.slilio.sql2codeInitDemo.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.slilio.sql2codeInitDemo.utils.DateUtils;
import com.slilio.sql2codeInitDemo.enums.DateTimePatternEnum;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Description: 产品信息
 *
 * @Author: slilio
 * @CreateTime: 2025-07-23
 */
public class ProductInfo implements Serializable {
	/**
	 * 自增ID
	 */
	private Integer id;

	/**
	 * 厂商ID
	 */
	@JsonIgnore
	private String companyId;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 产品名
	 */
	private String productName;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 创建日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	/**
	 * 库存
	 */
	private Long stock;

	/**
	 * 状态
	 */
	@JsonIgnore
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

	@Override
	public String toString() {
		return  " 自增ID: " + ( id == null ? "空" : id ) +  ", 厂商ID: " + ( companyId == null ? "空" : companyId ) +  ", 编码: " + ( code == null ? "空" : code ) +  ", 产品名: " + ( productName == null ? "空" : productName ) +  ", 价格: " + ( price == null ? "空" : price ) +  ", 类型: " + ( skuType == null ? "空" : skuType ) +  ", 颜色: " + ( colorType == null ? "空" : colorType ) +  ", 创建时间: " + ( createTime == null ? "空" : DateUtils.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()) ) +  ", 创建日期: " + ( createDate == null ? "空" : DateUtils.format(createDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()) ) +  ", 库存: " + ( stock == null ? "空" : stock ) +  ", 状态: " + ( status == null ? "空" : status ) +  ", 删除状态：0：删除；1：正常: " + ( isDel == null ? "空" : isDel );
	}
}