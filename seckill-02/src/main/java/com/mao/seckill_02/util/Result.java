package com.mao.seckill_02.util;

/**
 * 通用的返回结果
 * 
 * @author 71979
 *
 */
public class Result<T> {

	private CodeMsg codeMsg;
	private T data;

	//构造函数私有
	private Result(CodeMsg codeMsg, T data) {
		if(codeMsg == null){
			return;
		}
		this.codeMsg = codeMsg;
		this.data = data;
	}
	//构造器私有
	private Result(CodeMsg codeMsg) {
		if(codeMsg == null){
			return;
		}
		this.codeMsg = codeMsg;
		this.data = null;
	}

	// 成功时调用
	public static <T> Result<T> success(T data) {
		return new Result<T>(CodeMsg.SUCCESS, data);
	}

	// 异常时调用
	public static <T> Result<T> ServerError(CodeMsg codeMsg) {
		return new Result<T>(codeMsg);
//		if(codeMsg.getCode() == 500100){
//			return new Result<T>(codeMsg.SERVER_ERROR);
//		}else if(codeMsg.getCode() == 500200){
//			return new Result<T>(codeMsg.PRODUCTSERVICE_ERROR);
//		}else if(codeMsg.getCode() == 500201){
//			return new Result<T>(codeMsg.USER_ERROR);
//		}else if(codeMsg.getCode() == 500202){
//			return new Result<T>(codeMsg.PASSWORD_ERROR);
//		}else if(codeMsg.getCode() == 500203){
//			return new Result<T>(codeMsg.COUNT_ERROR);
//		}else if(codeMsg.getCode() == 500204){
//			return new Result<T>(codeMsg.PASSWORD_NULL_ERROR);
//		}else if(codeMsg.getCode() == 500101){
//			return new Result<T>(codeMsg.BIND_ERROR.fillArgs(args));
//		}else if(codeMsg.getCode() == 500204){
//			return new Result<T>(codeMsg.PASSWORD_NULL_ERROR);
//		}else{
//			return new Result<T>(codeMsg.PRODUCTSERVICE_ERROR);
//		}
	}

	public CodeMsg getCodeMsg() {
		return codeMsg;
	}

	public T getData() {
		return data;
	}
	@Override
	public String toString() {
		return "Result [codeMsg=" + codeMsg + ", data=" + data + "]";
	}	
	
}
