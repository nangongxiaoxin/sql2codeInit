package com.slilio.sql2codeInitDemo.controller;

import com.slilio.sql2codeInitDemo.entity.po.ProductInfo;
import com.slilio.sql2codeInitDemo.entity.query.ProductInfoQuery;
import com.slilio.sql2codeInitDemo.entity.vo.ResponseVO;
import com.slilio.sql2codeInitDemo.service.ProductInfoService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 产品信息Controller
 *
 * @Author: slilio
 * @CreateTime: 2025-08-10
 */
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController extends ABaseController {

	@Resource private ProductInfoService productInfoService;

	@RequestMapping("loadDataList")
	public ResponseVO loadDataList(ProductInfoQuery query) {
		return getSuccessResponseVO(productInfoService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	public ResponseVO add(ProductInfo bean) {
		this.productInfoService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	public ResponseVO addBatch(@RequestBody List<ProductInfo> listBean) {
		this.productInfoService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增或修改
	 */
	public ResponseVO addOrUpdateBatch(@RequestBody List<ProductInfo> listBean) {
		this.productInfoService.addOrUpdateBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据 Id 查询
	 */
	public ResponseVO getProductInfoById (Integer id) {
		return getSuccessResponseVO(this.productInfoService.getProductInfoById(id));
	}

	/**
	 * 根据 Id 更新
	 */
	public ResponseVO updateProductInfoById (ProductInfo bean, Integer id) {
		this.productInfoService.updateProductInfoById(bean, id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据 Id 删除
	 */
	public ResponseVO deleteProductInfoById (Integer id) {
		this.productInfoService.deleteProductInfoById(id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据 Code 查询
	 */
	public ResponseVO getProductInfoByCode (String code) {
		return getSuccessResponseVO(this.productInfoService.getProductInfoByCode(code));
	}

	/**
	 * 根据 Code 更新
	 */
	public ResponseVO updateProductInfoByCode (ProductInfo bean, String code) {
		this.productInfoService.updateProductInfoByCode(bean, code);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据 Code 删除
	 */
	public ResponseVO deleteProductInfoByCode (String code) {
		this.productInfoService.deleteProductInfoByCode(code);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据 SkuTypeAndColorType 查询
	 */
	public ResponseVO getProductInfoBySkuTypeAndColorType (Integer skuType, Integer colorType) {
		return getSuccessResponseVO(this.productInfoService.getProductInfoBySkuTypeAndColorType(skuType, colorType));
	}

	/**
	 * 根据 SkuTypeAndColorType 更新
	 */
	public ResponseVO updateProductInfoBySkuTypeAndColorType (ProductInfo bean, Integer skuType, Integer colorType) {
		this.productInfoService.updateProductInfoBySkuTypeAndColorType(bean, skuType, colorType);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据 SkuTypeAndColorType 删除
	 */
	public ResponseVO deleteProductInfoBySkuTypeAndColorType (Integer skuType, Integer colorType) {
		this.productInfoService.deleteProductInfoBySkuTypeAndColorType(skuType, colorType);
		return getSuccessResponseVO(null);
	}


}