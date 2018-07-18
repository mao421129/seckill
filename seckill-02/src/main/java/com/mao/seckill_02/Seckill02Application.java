package com.mao.seckill_02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.mao.seckill_02.listener.InitListener;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SpringBootApplication
public class Seckill02Application {

	@Bean
	public Jedis jedis(){
		JedisPool jedisPool = null;
		try {
			JedisPoolConfig jpconfig = new JedisPoolConfig();
			jpconfig.setMaxTotal(100);
			jpconfig.setMaxIdle(5);
			jpconfig.setMaxWaitMillis(1000*10);
			jpconfig.setTestOnBorrow(true);
			jedisPool = new JedisPool(jpconfig, "192.168.1.10", 6400, 20000);	
			return jedisPool.getResource();
		}finally{
			if(jedisPool != null){
				jedisPool.close();
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletListenerRegistrationBean servletListenerRegistrationBean(){
		ServletListenerRegistrationBean servletListenerRegistrationBean =
				new ServletListenerRegistrationBean();
		servletListenerRegistrationBean.setListener(new InitListener());
		return servletListenerRegistrationBean;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Seckill02Application.class, args);
	}
}
