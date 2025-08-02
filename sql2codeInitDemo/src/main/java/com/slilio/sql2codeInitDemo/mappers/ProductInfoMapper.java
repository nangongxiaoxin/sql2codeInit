package com.slilio.sql2codeInitDemo.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: 产品信息Mapper
 *
 * @Author: slilio
 * @CreateTime: 2025-08-03
 */
public interface ProductInfoMapper<T, P> extends BaseMapper {
	/**
	 * 根据 Id 查询
	 */
	T selectById (@Param("id") Integer id);

	/**
	 * 根据 Id 更新
	 */
	Long updateById (@Param("bean") T t, @Param("id") Integer id);

	/**
	 * 根据 Id 删除
	 */
	Long deleteById (@Param("id") Integer id);

	/**
	 * 根据 Code 查询
	 */
	T selectByCode (@Param("code") String code);

	/**
	 * 根据 Code 更新
	 */
	Long updateByCode (@Param("bean") T t, @Param("code") String code);

	/**
	 * 根据 Code 删除
	 */
	Long deleteByCode (@Param("code") String code);

	/**
	 * 根据 SkuTypeAndColorType 查询
	 */
	T selectBySkuTypeAndColorType (@Param("skuType") Integer skuType, @Param("colorType") Integer colorType);

	/**
	 * 根据 SkuTypeAndColorType 更新
	 */
	Long updateBySkuTypeAndColorType (@Param("bean") T t, @Param("skuType") Integer skuType, @Param("colorType") Integer colorType);

	/**
	 * 根据 SkuTypeAndColorType 删除
	 */
	Long deleteBySkuTypeAndColorType (@Param("skuType") Integer skuType, @Param("colorType") Integer colorType);


}