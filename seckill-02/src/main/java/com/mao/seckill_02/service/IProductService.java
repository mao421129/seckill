package com.mao.seckill_02.service;

import java.util.List;

import com.mao.seckill_02.domain.Product;
import com.mao.seckill_02.domain.ProductVo;

/**
 * 商品的增删改
 * @author 71979
 *
 */
public interface IProductService {

	Product getByProductId(Long productId);

	List<Product> getAllProduct();

	List<ProductVo> getAllProductVo();

	ProductVo getProductVoByProductId(Long productId);

	int judgeTime(ProductVo productVo);

	void reduseStockCount(Long productId);
	
}
