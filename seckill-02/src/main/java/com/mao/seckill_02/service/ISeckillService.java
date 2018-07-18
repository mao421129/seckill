package com.mao.seckill_02.service;

import com.mao.seckill_02.domain.OrderInfo;
import com.mao.seckill_02.domain.ProductVo;
import com.mao.seckill_02.domain.SeckillProduct;

/**
 * 秒杀操作相关的service
 * @author 71979
 *
 */
public interface ISeckillService {

	/**
	 * 判断秒杀时间是否准确
	 * @param productVo
	 */
	void judgeSeckillSeconds(ProductVo productVo);
	
	/**
	 * 秒杀相关操作
	 * @param productVo
	 * @return 
	 */
	OrderInfo doSeckill(SeckillProduct sp, Long userId);

	Long getSecKillResult(Long id, Long productId);

	/**
	 * 生成秒杀验证path，隐藏path
	 * @param userId
	 * @param productId
	 * @return
	 */
	String firtPath(Long userId, Long productId);

	/**
	 * 验证秒杀path
	 * @param userId
	 * @param productId
	 * @param path
	 * @return
	 */
	boolean validatePath(Long userId, Long productId, String path);

}
