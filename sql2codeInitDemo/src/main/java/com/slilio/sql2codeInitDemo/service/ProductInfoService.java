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

	/**
	 * 新增
	 */
	Long add(ProductInfo bean);

	/**
	 * 批量新增
	 */
	Long addBatch(List<ProductInfo> listBean);

	/**
	 * 批量新增或修改
	 */
	Long addOrUpdateBatch(List<ProductInfo> listBean);

	/**
	 * 根据 Id 查询
	 */
	ProductInfo getById (Integer id);

	/**
	 * 根据 Id 更新
	 */
	Long updateById (ProductInfo bean, Integer id);

	/**
	 * 根据 Id 删除
	 */
	Long deleteById (Integer id);

	/**
	 * 根据 Code 查询
	 */
	ProductInfo getByCode (String code);

	/**
	 * 根据 Code 更新
	 */
	Long updateByCode (ProductInfo bean, String code);

	/**
	 * 根据 Code 删除
	 */
	Long deleteByCode (String code);

	/**
	 * 根据 SkuTypeAndColorType 查询
	 */
	ProductInfo getBySkuTypeAndColorType (Integer skuType, Integer colorType);

	/**
	 * 根据 SkuTypeAndColorType 更新
	 */
	Long updateBySkuTypeAndColorType (ProductInfo bean, Integer skuType, Integer colorType);

	/**
	 * 根据 SkuTypeAndColorType 删除
	 */
	Long deleteBySkuTypeAndColorType (Integer skuType, Integer colorType);


}