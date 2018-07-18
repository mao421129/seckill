package com.mao.seckill_02.redis;

import com.mao.seckill_02.domain.OrderInfo;
import com.mao.seckill_02.domain.SeckillProduct;
/**
 * 通用的redis的PrefixKey
 * @author 71979
 *
 */
public class RedisKey {

	private int expireSeconds;
	private String prefix;
	
	public static RedisKey token = new RedisKey(3000, "TOKEN_SESSIONID_");
	
	public static RedisKey preKeyOfSeckillProductStock = new RedisKey(SeckillProduct.class);
	public static RedisKey orderInfoPreKey = new RedisKey(OrderInfo.class);
	public static RedisKey pathPreKey = new RedisKey(3000,"seckillPath");
	//默认过期时间为0，永不过期,通过Bean.calss创建
	public <T> RedisKey(Class<T> calzz){
		if(calzz.getSimpleName().equals("User")){
			this.expireSeconds = 0;	
		}
		this.expireSeconds = 3000;
		this.prefix = calzz.getSimpleName()+ "Key_Id_";;
	}
	
	//有过期时间，通过Bean.class创建
	public <T> RedisKey(int expireSeconds,Class<T> calzz){
		this.expireSeconds = expireSeconds;
		this.prefix = calzz.getSimpleName() + "Key_Id_";
	}

	//特定RedisKey，传入String创建
	public RedisKey(int expireSeconds, String string) {
		this.expireSeconds = expireSeconds;
		this.prefix = string;
	}

	public int getExpireSeconds() {
		return expireSeconds;
	}

	public String getPrefix() {
		return prefix;
	}

	public static void main(String[] args) {
		System.out.println(RedisKey.preKeyOfSeckillProductStock.getPrefix());
	}
}
