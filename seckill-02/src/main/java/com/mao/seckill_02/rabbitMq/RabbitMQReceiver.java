package com.mao.seckill_02.rabbitMq;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mao.seckill_02.domain.SeckillMessage;
import com.mao.seckill_02.domain.SeckillOrder;
import com.mao.seckill_02.domain.SeckillProduct;
import com.mao.seckill_02.domain.User;
import com.mao.seckill_02.exception.MyException;
import com.mao.seckill_02.redis.RedisClient;
import com.mao.seckill_02.service.IOrderService;
import com.mao.seckill_02.service.ISeckillProductService;
import com.mao.seckill_02.service.ISeckillService;
import com.mao.seckill_02.util.CodeMsg;

/**
 * seckill receiver
 * @author 71979
 *
 */
@Component("mqReceiver")
@RabbitListener(queues="seckill")
public class RabbitMQReceiver {
	
	@Autowired
	private ISeckillService seckillService;
	@Autowired
	private ISeckillProductService seckillProductService;
	@Autowired
	private IOrderService orderService;
	private static Logger log = org.slf4j.LoggerFactory.getLogger(RabbitMQReceiver.class);
	/**
	 * Direct模式 交换机Exchange
	 * @param message
	 */
	@RabbitHandler
	public void process(String message){
		log.info("receiver message: " + message);
		SeckillMessage sm = RedisClient.jsonToBean(message, SeckillMessage.class);
		if(sm != null){
			User u = sm.getUser();
			Long productId = sm.getProductId();
			SeckillProduct sp = this.seckillProductService.getSeckillProductByProductId(productId);
			if(sp == null){
				throw new MyException(CodeMsg.SECKILLPRODUCT_NULL_ERROR);
			}
			//判断库存
			if(sp.getStockCount() <= 0){
				return;
			}
			//判断是否是重复秒杀
			SeckillOrder order = this.orderService.getSeckillOrderByUserIdAndProductId(u.getId(), productId);
			if(order != null){
				return;
			}
			//生成订单
			this.seckillService.doSeckill(sp, u.getId());
		}
	}
}
