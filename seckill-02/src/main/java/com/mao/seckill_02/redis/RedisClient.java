package com.mao.seckill_02.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
@Component
public class RedisClient {

	@Autowired
	private Jedis jedis;
	
	/**
	 * 通用，使用类名作为prefixkey
	 * @param id
	 * @param value
	 * @return
	 */
	public <T> boolean setToRedis(Long id, T value) {
		if (value == null) {
			return false;
		}
		String key = String.valueOf(id);
		RedisKey redisKey = new RedisKey(value.getClass());
		String realKey = redisKey.getPrefix() + key;
		String target = beanToJson(value);
		if (StringUtils.isEmpty(target)) {
			return false;
		}
		if(redisKey.getExpireSeconds() <= 0){
			jedis.set(realKey, target);
		}else{
			jedis.setex(realKey, redisKey.getExpireSeconds(), target);
		}
		return true;
	}
	/**
	 * 特殊，固定的prefixKey
	 * @param prefixKey
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> boolean setToRedis(RedisKey prefixKey, String key, T value) {
		if (value == null) {
			return false;
		}
		String realKey = prefixKey.getPrefix() + key;
		String target = beanToJson(value);
		if (StringUtils.isEmpty(target)) {
			return false;
		}
		if(prefixKey.getExpireSeconds() <= 0){
			jedis.set(realKey, target);
		}else{
			jedis.setex(realKey, prefixKey.getExpireSeconds(), target);
		}
		return true;
	}

	//通用的通过id来取bean
	public <T> T getFromRedis(Long id, Class<T> calzz) {
		String prefix = new RedisKey(calzz).getPrefix();
		String key = prefix + String.valueOf(id);
		String jsonString = jedis.get(key);
		return jsonToBean(jsonString, calzz);
	}

	public int getFromRedis(RedisKey redisKey, String key){
		return Integer.valueOf(jedis.get(redisKey + key));
	}
	//通过key来取bean
	//通用的通过id来取bean
	public <T> T getFromRedis(RedisKey redisKey, String key, Class<T> calzz) {
		String prefix = redisKey.getPrefix();
		String realKey = prefix + key;
		String jsonString = jedis.get(realKey);
		return jsonToBean(jsonString, calzz);
	}
	
	// 自增
	public Long incr(String prefix, String realKey) {
		String key = prefix + realKey;
		Long incr = jedis.incr(key);
		return incr;
	}

	// 自减
	public Long decr(String prefix, String realKey) {
		String key = prefix + realKey;
		Long decr = jedis.decr(key);
		return decr;
	}

	// 判断对应的Bean的key是否存在
	public <T> boolean BeanKeyExists(String key, Class<T> calzz) {
		String realKey = new RedisKey(calzz).getPrefix() + key;
		return this.jedis.exists(realKey);

	}

	@SuppressWarnings("unchecked")
	public static <T> T jsonToBean(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString) || clazz == null) {
			return null;
		}
		if (clazz == int.class || clazz == Integer.class) {
			return (T) Integer.valueOf(jsonString);
		} else if (clazz == String.class) {
			return (T) jsonString;
		} else if (clazz == long.class || clazz == Long.class) {
			return (T) Long.valueOf(jsonString);
		} else {
			T t = JSONObject.toJavaObject(JSONObject.parseObject(jsonString), clazz);
			return t;
		}
	}

	public static <T> String beanToJson(T value) {
		if (value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if (clazz == int.class || clazz == Integer.class) {
			return "" + value;
		} else if (clazz == String.class) {
			return (String) value;
		} else if (clazz == long.class || clazz == Long.class) {
			return "" + value;
		} else {
			String jsonString = JSONObject.toJSONString(value);
			return jsonString;
		}
	}
}
