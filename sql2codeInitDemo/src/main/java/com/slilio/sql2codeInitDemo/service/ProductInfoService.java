package com.slilio.sql2codeInitDemo.service;

import com.slilio.sql2codeInitDemo.entity.vo.PaginationResultVO;
import com.slilio.sql2codeInitDemo.entity.po.ProductInfo;
import com.slilio.sql2codeInitDemo.entity.query.ProductInfoQuery;
import java.util.List;

/**
 * @Description: 产品信息Service
 *
 * @Author: slilio
 * @CreateTime: 2025-08-03
 */
public interface ProductInfoService {

	/**
	 * 根据条件查询列表
	 */
	List<ProductInfo> findListByParam(ProductInfoQuery param);

	/**
	 * 根据条件查询数量
	 */
	Long findCountByParam(ProductInfoQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<ProductInfo> findListByPage(ProductInfoQuery query);


}