package com.mao.seckill_02.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mao.seckill_02.controller.ProductController;
import com.mao.seckill_02.domain.OrderInfo;
import com.mao.seckill_02.domain.ProductVo;
import com.mao.seckill_02.domain.SeckillOrder;
import com.mao.seckill_02.domain.SeckillProduct;
import com.mao.seckill_02.exception.MyException;
import com.mao.seckill_02.redis.RedisClient;
import com.mao.seckill_02.redis.RedisKey;
import com.mao.seckill_02.service.IOrderService;
import com.mao.seckill_02.service.ISeckillProductService;
import com.mao.seckill_02.service.ISeckillService;
import com.mao.seckill_02.util.CodeMsg;
import com.mao.seckill_02.util.MD5Utils;
import com.mao.seckill_02.util.UUIDUtil;

@Service("seckillService")
public class SeckillServiceImpl implements ISeckillService {

	@Autowired
	private ISeckillProductService seckillProductService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private RedisClient redisClient;
	private static Logger log = org.slf4j.LoggerFactory.getLogger(SeckillServiceImpl.class);
	private boolean orderMark = true;
	
	public void judgeSeckillSeconds(ProductVo productVo) {
		long cur = System.currentTimeMillis();
		if(cur < productVo.getStartDate().getTime() || cur > productVo.getEndDate().getTime()){
			throw new MyException(CodeMsg.SECKILL_ERROR);
		}
	}
	
	//秒杀操作
	@Transactional
	public OrderInfo doSeckill(SeckillProduct sp, Long userId) {
		//减少秒杀商品库存,直接库存减一就可以
		boolean success = 
				this.seckillProductService.updateStockCount(sp.getProductId());
		if(success){//减库存成功
			//下订单seckillOrder OrderInfo，创建秒杀订单，详细订单
			OrderInfo orderInfo = orderService.creatSeckillOrderAndOrderInfo(sp, userId);
			return orderInfo;	
		}else{//减库存失败，已经秒杀完
//			redisClient.incr(RedisKey.preKeyOfSeckillProductStock.getPrefix(),
//					String.valueOf(sp.getProductId()));
			//做一个判断标记
			this.orderMark = false;
			return null;
		}
	}

	/**
	 * 获取秒杀结果
	 */
	public Long getSecKillResult(Long id, Long productId) {
		SeckillOrder seckillorder = orderService.getSeckillOrderByUserIdAndProductId(id, productId);
		if(seckillorder != null){
			return seckillorder.getOrderId();//秒杀成功
		}else{
			if(orderMark){
				return 0L;//继续排队
			}else{
				return -1L;//已经秒杀完，失败
			}
		}
	}

	

	public String firtPath(Long userId, Long productId) {
		//生成单独的path
		String path = MD5Utils.md5(UUIDUtil.uuid()+"mao");
		log.error(path);
		//存入redis
		redisClient.setToRedis(RedisKey.pathPreKey, "_" + userId + "_" + productId, path);
		return null;
	}

	public boolean validatePath(Long userId, Long productId, String path) {
		if(productId == null || path == null){
			return false;
		}
		String vPaht = this.redisClient.getFromRedis(RedisKey.pathPreKey, "_" + userId + "_" + productId, String.class);
		if(vPaht == null || !vPaht.equals(path)){
			return false;
		}
		
		return true;
	}

	public boolean isOrderMark() {
		return orderMark;
	}

	public void setOrderMark(boolean orderMark) {
		this.orderMark = orderMark;
	}
	
}
