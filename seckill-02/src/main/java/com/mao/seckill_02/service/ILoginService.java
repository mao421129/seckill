package com.mao.seckill_02.service;

import javax.servlet.http.HttpServletResponse;

import com.mao.seckill_02.vo.LoginVo;

public interface ILoginService {
	
	boolean login(HttpServletResponse resp, LoginVo vo);
	
}
