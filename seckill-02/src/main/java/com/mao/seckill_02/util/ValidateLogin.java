package com.mao.seckill_02.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.mao.seckill_02.vo.LoginVo;

public class ValidateLogin {

	private static Pattern pattern = Pattern.compile("1\\d{10}");
	
	public static boolean validate(LoginVo vo){
		if(validateMobile(vo.getMobile()) && validatePassword(vo.getPassword())){
			return true;
		}
		return false;
	}

	
	public static boolean validateMobile(String mobile) {
		return pattern.matcher(mobile).matches();
	}; 
	
	public static boolean validatePassword(String password) {		
		return !StringUtils.isEmpty(password);
	}
	
	public static void main(String[] args) {
		System.out.println(validateMobile("32222222222"));
	}
}
