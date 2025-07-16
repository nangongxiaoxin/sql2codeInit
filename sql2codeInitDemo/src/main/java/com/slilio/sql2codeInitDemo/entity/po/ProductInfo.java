package com.slilio.sql2codeInitDemo.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Description: 产品信息
 *
 * @Author: slilio
 * @CreateTime: 2025-07-16
 */
public class ProductInfo implements Serializable {
	/**
	 * 自增ID
	 */
	private Integer id;

	/**
	 * 厂商ID
	 */
	@JsonIgnore	private String companyId;

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
	@JsonIgnore	private Integer status;

}