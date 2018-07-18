package com.mao.seckill_02.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * 双重MD5
 * @author 71979
 *
 */
@Component
public class MD5Utils {

	private static final String salts = "mao";
	
	public static String md5(String str){
		return DigestUtils.md5Hex(str);
	}
	
	//从服务端传送过来的
	public static String inputPassFormPass(String inputPass){
		String str = salts + inputPass;
		return md5(str);
	}
	
	//存db，随机的salt
	public static String formPassToDB(String formPass,String salt){
		String str = formPass + salt;
		return md5(str);
	}
	
	//从客户端传回服务端，双重加密 salt :yu
	public static String inputPassToDb(String inputPass, String salt){
		String input = inputPassFormPass(inputPass);
		String form = formPassToDB(input, salt);
		return form;
	}
	
	public static void main(String[] args) {
		String inputPassFormPass = inputPassFormPass("123456");
		System.out.println(inputPassFormPass);
		String inputPassToDb = inputPassToDb("3fe8ebd7f5996651fa46c4aefe24b6af", "yu");
		System.out.println(inputPassToDb);
	}
}
