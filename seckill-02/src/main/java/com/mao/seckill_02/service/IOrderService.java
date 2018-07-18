package com.mao.seckill_02.service;

import com.mao.seckill_02.domain.OrderInfo;
import com.mao.seckill_02.domain.SeckillOrder;
import com.mao.seckill_02.domain.SeckillProduct;

public interface IOrderService {

	SeckillOrder getSeckillOrderByUserIdAndProductId(Long userId, Long productId);

	OrderInfo creatSeckillOrderAndOrderInfo(SeckillProduct sp, Long userId);
}
