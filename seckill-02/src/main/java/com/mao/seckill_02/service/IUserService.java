package com.mao.seckill_02.service;

import javax.servlet.http.HttpServletResponse;

public interface IUserService {

	<T> T getValueFormCookies(HttpServletResponse resp,String uuid, Class<?> clazz);
}
