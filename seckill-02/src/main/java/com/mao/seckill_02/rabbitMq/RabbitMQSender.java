package com.mao.seckill_02.rabbitMq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("mqSender")
public class RabbitMQSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	/*
	 * 秒杀商品队列
	 */
	public void send(String message){
		this.rabbitTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, message);
	};
}
