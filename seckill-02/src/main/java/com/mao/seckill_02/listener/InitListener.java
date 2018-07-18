package com.mao.seckill_02.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mao.seckill_02.domain.SeckillProduct;
import com.mao.seckill_02.redis.RedisClient;
import com.mao.seckill_02.redis.RedisKey;
import com.mao.seckill_02.service.ISeckillProductService;
import com.mao.seckill_02.springContext.SpringContext;
/**
 * 监听器
 * 启动spring判断数据库中是否有秒杀商品
 * 有则将秒杀商品的库存存入redis
 * @author 71979
 *
 */
public class InitListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		WebApplicationContext webApplicationContext = 
				WebApplicationContextUtils.getWebApplicationContext(sc);
		SpringContext.setApplicationContext(webApplicationContext);
		/*
		ISeckillProductService seckillProductService = 
				webApplicationContext.getBean(ISeckillProductService.class);
		RedisClient redisClient = webApplicationContext.getBean(RedisClient.class);
		
		List<SeckillProduct> allSeckillProduct = 
				seckillProductService.getAllSeckillProduct();
		if(allSeckillProduct.size() > 0){
			for (SeckillProduct seckillProduct : allSeckillProduct) {
				int stock = seckillProduct.getStockCount();
				String key = String.valueOf(seckillProduct.getProductId());
				redisClient.setToRedis(RedisKey.preKeyOfSeckillProductStock, key, stock);
			}
		}*/		
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	
	}
}
