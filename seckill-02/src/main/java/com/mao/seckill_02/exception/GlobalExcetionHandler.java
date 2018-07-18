package com.mao.seckill_02.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mao.seckill_02.util.CodeMsg;
import com.mao.seckill_02.util.Result;

@ControllerAdvice
// 方便输出，跟Controller一样了
@ResponseBody
public class GlobalExcetionHandler {
	// 表示所有的异常都需要拦截
	// 拦截特定的异常，就写 特定异常.class
	@ExceptionHandler(value = Exception.class)
	public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {// 同controller
		e.printStackTrace();
		if (e instanceof BindException) {// 如果是绑定异常，应该就是标签所綁定的异常
			System.out.println("进入到绑定异常");
			BindException ex = (BindException) e;
			List<ObjectError> errors = ex.getAllErrors();
			ObjectError objectError = errors.get(0);
			String args = objectError.getDefaultMessage();
			// 传入默认的message,就是在标签中定义的
			System.out.println("args:" + args);
			CodeMsg finnal = CodeMsg.BIND_ERROR.fillArgs(args);
			System.out.println("最终返回message:" + finnal.getMsg());
			return Result.ServerError(finnal);
		} else if (e instanceof MyException) {
			MyException me = (MyException) e;
			// 拿到message throw new MyException(CodeMsg.SERVER_ERROR);
			me.getMsg();
			return Result.ServerError(me.getMsg());
		} else {
			System.out.println("最终进入到终极异常");
			return Result.ServerError(CodeMsg.SERVER_ERROR);
		}
	}
}
