package com.mao.seckill_02.service;

import java.util.List;

import com.mao.seckill_02.domain.SeckillProduct;

/**
 * 秒杀订单Service
 * @author 71979
 *
 */
public interface ISeckillProductService {

	//减少秒杀商品库存，每次减少一次
	boolean updateStockCount(Long productId);

	SeckillProduct getSeckillProductByProductId(Long productId);
	
	List<SeckillProduct> getAllSeckillProduct();
}
