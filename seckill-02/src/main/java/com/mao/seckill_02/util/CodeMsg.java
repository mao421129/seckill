package com.mao.seckill_02.util;

/**
 * 通用的异常返回code处理
 * 
 * @author 71979
 *
 */
public class CodeMsg {

	private int code;
	private String msg;

	// 通用成功处理------------------------------------------------
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	// 服务端异常 500100-------------------------------------------------
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	// 绑定异常 500101
	public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常:%s");
	
	public static CodeMsg OPERATION_ERROR = new CodeMsg(500102, "操作异常");
	// 登陆异常 500200------------------------------------
	public static CodeMsg LOGIN_ERROR = new CodeMsg(500200, "登陆异常");
	// 账号不存在500201
	public static CodeMsg USER_ERROR = new CodeMsg(500201, "用户不存在");
	// 密码错误 500202
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(500202, "密码错误");
	// 账号格式错误 500203
	public static CodeMsg COUNT_ERROR = new CodeMsg(500203, "账号格式错误");
	// 密码错误 500204
	public static CodeMsg PASSWORD_NULL_ERROR = new CodeMsg(500204, "密码不能为空");
	
	// 商品模块异常5003XX------------------------------------------
	public static CodeMsg PRODUCTSERVICE_ERROR = new CodeMsg(500300, "商品模块异常");
	public static CodeMsg SECKILLPRODUCT_NULL_ERROR = new CodeMsg(500303, "商品异常，该秒杀商品不村子啊");
	public static CodeMsg SECKILL_ERROR = new CodeMsg(500301, "秒杀未开始或已结束");
	public static CodeMsg SECKILL_REPEAT_ERROR = new CodeMsg(500302, "您已秒杀成功，不能重复秒杀");
	//订单模块5004
	
	//库存模块5005
	public static CodeMsg STOCK_ERROR = new CodeMsg(500501, "库存不足");
	
	//往CodeMsg中填充参数
	public CodeMsg fillArgs(Object...args){
		int code = this.code;
		//将参数与message绑定
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}
	
	public CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		return "CodeMsg [code=" + code + ", msg=" + msg + "]";
	}

}
