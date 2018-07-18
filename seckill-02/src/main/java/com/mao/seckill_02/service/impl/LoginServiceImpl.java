package com.mao.seckill_02.service.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mao.seckill_02.dao.LoginMapper;
import com.mao.seckill_02.domain.User;
import com.mao.seckill_02.exception.MyException;
import com.mao.seckill_02.redis.RedisClient;
import com.mao.seckill_02.redis.RedisKey;
import com.mao.seckill_02.service.ILoginService;
import com.mao.seckill_02.util.CodeMsg;
import com.mao.seckill_02.util.CookieUtil;
import com.mao.seckill_02.util.MD5Utils;
import com.mao.seckill_02.util.UUIDUtil;
import com.mao.seckill_02.vo.LoginVo;

@Service("loginService")
public class LoginServiceImpl implements ILoginService{

	@Autowired
	private LoginMapper loginMapper;
	@Autowired
	private RedisClient redisClient;
	
	public static final String COOKIE_TOKEN_KEY = "token";
	//3bb1ce8770990392b7bc77155e33f928
	//89832a62319bc94e037a693825379326
	public boolean login(HttpServletResponse resp, LoginVo vo) {
		if(vo == null || vo.getMobile()==null || vo.getPassword() == null){
			throw new MyException(CodeMsg.SERVER_ERROR);
		}
		Long id = Long.valueOf(vo.getMobile());
		User u = this.loginMapper.getUserById(id);
		if(u == null){
			throw new MyException(CodeMsg.USER_ERROR);
		}
		String password = vo.getPassword();
		String realPassword = MD5Utils.formPassToDB(password, u.getSalt());
		if(!realPassword.equals(u.getPassword())){
			throw new MyException(CodeMsg.PASSWORD_ERROR);
		}
		
		//全局session:存储在中：key：TOKEN_SESSIONID_uuid;value:user
		String token = UUIDUtil.uuid();
		RedisKey prefixKey = RedisKey.token;
		redisClient.setToRedis(prefixKey, token, u);
		CookieUtil.setCookie(resp, token, prefixKey.getExpireSeconds());
		return true;
	}
}
