package com.mao.seckill_02.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static final String COOKIE_TOKEN_KEY = "token";
	
	public static boolean setCookie(HttpServletResponse resp, String token ,int expireTime) {
		Cookie cookie = new Cookie(COOKIE_TOKEN_KEY, token);
		cookie.setMaxAge(expireTime);
		cookie.setPath("/");
		resp.addCookie(cookie);
		return true;
	}
}
