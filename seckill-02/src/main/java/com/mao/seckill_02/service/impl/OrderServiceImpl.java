package com.mao.seckill_02.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mao.seckill_02.dao.OrderInfoMapper;
import com.mao.seckill_02.dao.SeckillOrderMapper;
import com.mao.seckill_02.domain.OrderInfo;
import com.mao.seckill_02.domain.Product;
import com.mao.seckill_02.domain.SeckillOrder;
import com.mao.seckill_02.domain.SeckillProduct;
import com.mao.seckill_02.redis.RedisClient;
import com.mao.seckill_02.redis.RedisKey;
import com.mao.seckill_02.service.IOrderService;
import com.mao.seckill_02.service.IProductService;

@Service("orderService")
public class OrderServiceImpl implements IOrderService{
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private SeckillOrderMapper seckillOrderMapper;
	@Autowired
	private IProductService productService;
	@Autowired
	private RedisClient redisClient;
	@Transactional
	public OrderInfo creatSeckillOrderAndOrderInfo(SeckillProduct sp, Long userId) {
		Product p = this.productService.getByProductId(sp.getProductId());
		//1 生成订单
		OrderInfo o = new OrderInfo();
		o.setCreatDate(new Date());
		o.setOrderChannel(0);
		o.setDeliveryAddrId(0L);
		o.setProductCount(1);
		o.setProductId(sp.getProductId());
		o.setProductName(p.getProductName());
		o.setProductPrice(sp.getSeckillPrice());
		o.setStatus(0);
		o.setUserId(userId);
		//不能这样写，返回的是受影响的行数
		//long orderInfoId = this.orderInfoMapper.add(o);
		this.orderInfoMapper.add(o);
		//2 生成秒杀订单
		Long orderInfoId = o.getId();
		SeckillOrder seco = new SeckillOrder();
		seco.setOrderId(orderInfoId);
		seco.setProductId(sp.getProductId());
		seco.setUserId(userId);
		this.seckillOrderMapper.add(seco);
		//3 存入缓存
		redisClient.setToRedis(RedisKey.orderInfoPreKey, userId + "_" + sp.getProductId(), o);
		return o;
	}

	@Override
	public SeckillOrder getSeckillOrderByUserIdAndProductId(Long userId, Long productId) {
		return this.seckillOrderMapper.getByUserIdAndProductId(userId, productId);
	}

}
