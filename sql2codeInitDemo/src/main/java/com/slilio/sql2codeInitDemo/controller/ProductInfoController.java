package com.slilio.sql2codeInitDemo.controller;

import com.slilio.sql2codeInitDemo.entity.po.ProductInfo;
import com.slilio.sql2codeInitDemo.entity.query.ProductInfoQuery;
import com.slilio.sql2codeInitDemo.entity.query.SimplePage;
import com.slilio.sql2codeInitDemo.entity.vo.PaginationResultVO;
import com.slilio.sql2codeInitDemo.enums.PageSize;
import com.slilio.sql2codeInitDemo.service.ProductInfoService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 产品信息Controller
 *
 * @Author: slilio
 * @CreateTime: 2025-08-09
 */
@RestController
public class ProductInfoController {

	@Resource private ProductInfoService productInfoService;

	/**
	 * 新增
	 */
	public Integer add(ProductInfo bean) {
		return this.productInfoService.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<ProductInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.productInfoService.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<ProductInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.productInfoService.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据 Id 查询
	 */
	public ProductInfo getProductInfoById (Integer id) {
		return this.productInfoService.selectById(id);
	}

	/**
	 * 根据 Id 更新
	 */
	public Integer updateProductInfoById (ProductInfo bean, Integer id) {
		return this.productInfoService.updateById(bean, id);
	}

	/**
	 * 根据 Id 删除
	 */
	public Integer deleteProductInfoById (Integer id) {
		return this.productInfoService.deleteById(id);
	}

	/**
	 * 根据 Code 查询
	 */
	public ProductInfo getProductInfoByCode (String code) {
		return this.productInfoService.selectByCode(code);
	}

	/**
	 * 根据 Code 更新
	 */
	public Integer updateProductInfoByCode (ProductInfo bean, String code) {
		return this.productInfoService.updateByCode(bean, code);
	}

	/**
	 * 根据 Code 删除
	 */
	public Integer deleteProductInfoByCode (String code) {
		return this.productInfoService.deleteByCode(code);
	}

	/**
	 * 根据 SkuTypeAndColorType 查询
	 */
	public ProductInfo getProductInfoBySkuTypeAndColorType (Integer skuType, Integer colorType) {
		return this.productInfoService.selectBySkuTypeAndColorType(skuType, colorType);
	}

	/**
	 * 根据 SkuTypeAndColorType 更新
	 */
	public Integer updateProductInfoBySkuTypeAndColorType (ProductInfo bean, Integer skuType, Integer colorType) {
		return this.productInfoService.updateBySkuTypeAndColorType(bean, skuType, colorType);
	}

	/**
	 * 根据 SkuTypeAndColorType 删除
	 */
	public Integer deleteProductInfoBySkuTypeAndColorType (Integer skuType, Integer colorType) {
		return this.productInfoService.deleteBySkuTypeAndColorType(skuType, colorType);
	}


}