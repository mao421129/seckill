package com.mao.seckill_02.controller;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mao.seckill_02.domain.SeckillProduct;
import com.mao.seckill_02.redis.RedisClient;
import com.mao.seckill_02.redis.RedisKey;
import com.mao.seckill_02.service.ISeckillProductService;

@Controller
public class SystemInitController implements InitializingBean{

	@Autowired
	private ISeckillProductService seckillProductService;
	@Autowired
	private RedisClient redisClient;

	@Override
	public void afterPropertiesSet() throws Exception {
		
			List<SeckillProduct> allSeckillProduct = 
					seckillProductService.getAllSeckillProduct();
			if(allSeckillProduct.size() > 0){
				for (SeckillProduct seckillProduct : allSeckillProduct) {
					int stock = seckillProduct.getStockCount();
					String key = String.valueOf(seckillProduct.getProductId());
					redisClient.setToRedis(RedisKey.preKeyOfSeckillProductStock, key, stock);
				}
			}	
		}
}
