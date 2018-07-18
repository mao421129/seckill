package com.mao.seckill_02.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.mao.seckill_02.domain.User;
import com.mao.seckill_02.service.IUserService;
import com.mao.seckill_02.util.CookieUtil;

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private IUserService userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 为true才往下执行
		Class<?> clazz = parameter.getParameterType();
		System.out.println(clazz.getSimpleName());
		// 遇到的参数是User，才往下执行
		return clazz == User.class;
	}

	@Override
	// 返回需要的这个对象
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// 拿到Request
		HttpServletRequest req = webRequest.getNativeRequest(HttpServletRequest.class);
		// 拿到Response
		HttpServletResponse resp = webRequest.getNativeResponse(HttpServletResponse.class);
		// 可能在参数中
		String paramterToken = req.getParameter(CookieUtil.COOKIE_TOKEN_KEY);
		// 可能在cookie中
		String cookieToken = getCookie(req, CookieUtil.COOKIE_TOKEN_KEY);

		if (paramterToken == null && cookieToken==null) {
			return null;
		}
		String token = StringUtils.isEmpty(paramterToken)? cookieToken : paramterToken;
		// 调用servie拿到需要返回的对象
		User u = this.userService.getValueFormCookies(resp, token, User.class);
		return u;
	}

	private String getCookie(HttpServletRequest req, String cookieTokenKey) {
		// 拿到Cookie
		Cookie[] cookies = req.getCookies();
		if(cookies == null || cookies.length <= 0){
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieTokenKey)) {
				// 拿到token
				return cookie.getValue();
			}
		}
		return null;
	}
}
