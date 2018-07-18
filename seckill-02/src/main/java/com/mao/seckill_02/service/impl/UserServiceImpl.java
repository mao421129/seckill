package com.mao.seckill_02.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.mao.seckill_02.dao.UserMapper;
import com.mao.seckill_02.domain.User;
import com.mao.seckill_02.exception.MyException;
import com.mao.seckill_02.redis.RedisClient;
import com.mao.seckill_02.redis.RedisKey;
import com.mao.seckill_02.service.IUserService;
import com.mao.seckill_02.util.CodeMsg;
import com.mao.seckill_02.util.CookieUtil;

@Service("commonService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private RedisClient redisClient;
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 通过id获取User
	 * @param userId
	 * @return
	 */
	public User getById(Long userId){
		//首先取出缓存中的数据
		User u = redisClient.getFromRedis(RedisKey.token, String.valueOf(userId), User.class);
		if(u != null){
			return u;
		}
		//拿不到则从数据库中拿
		u = userMapper.getById(userId);
		//再设置进缓存
		redisClient.setToRedis(RedisKey.token, String.valueOf(userId), u);
		return u;
	};
	
	/**
	 * 更新密码
	 * @param userId
	 * @param password
	 * @return
	 */
	public boolean updatePassword(Long userId, String password){
		User u = getById(userId);
		if(u == null){
			throw new MyException(CodeMsg.USER_ERROR);
		}
		this.userMapper.update(userId, password);
		//更新redis中的缓存
		redisClient.setToRedis(RedisKey.token, String.valueOf(userId), u);
		return true;
	}
	/**
	 * 通过cookie拿到token,再从缓存中拿到对应的对象
	 */
	public <T> T getValueFormCookies(HttpServletResponse resp, String token, Class<?> clazz) {
		if(StringUtils.isEmpty(token)){
			return null;
		}
		String key = RedisKey.token.getPrefix() + token;
		T t = (T) redisClient.getFromRedis(RedisKey.token, token, clazz);
		
		//重新设置有效期cookie
		CookieUtil.setCookie(resp, token, RedisKey.token.getExpireSeconds());
		if(t == null){
			return null;
		}
		return t;
	}

}
