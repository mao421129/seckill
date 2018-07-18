package com.mao.seckill_02.exception;

import com.mao.seckill_02.util.CodeMsg;
/**
 * 自定义的异常处理器
 * 还是需要交给ExceptionHandler处理
 * @author 71979
 *
 */
public class MyException extends RuntimeException{
	private static final long serialVersionUID = 7657134755295829681L;

	private CodeMsg msg;

	public MyException(CodeMsg msg) {
		super(msg.toString());
		this.msg = msg;
	}

	public CodeMsg getMsg() {
		return msg;
	}
}
