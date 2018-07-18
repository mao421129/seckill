package com.mao.seckill_02.util;

import java.util.UUID;

/**
 * 随机生成uuid
 * @author 71979
 *
 */
public class UUIDUtil {
	public static String uuid(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
