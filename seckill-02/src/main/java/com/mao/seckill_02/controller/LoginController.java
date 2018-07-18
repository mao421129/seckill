package com.mao.seckill_02.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mao.seckill_02.service.ILoginService;
import com.mao.seckill_02.util.Result;
import com.mao.seckill_02.vo.LoginVo;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private ILoginService loginService;

	// 使用log4j的log
	private static Logger log = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("/toLogin")
	public String toLogin() {
		return "login";
	}

	@RequestMapping("/doLogin")
	@ResponseBody
	public Result<String> doLogin(@Valid LoginVo vo, HttpServletResponse resp) {
		// 日志显示
		System.out.println("controller:" + vo.getPassword());
		log.info(vo.toString());
		// //参数校验
		// if(!ValidateLogin.validateMobile(vo.getMobile())){
		// return Result.ServerError(CodeMsg.COUNT_ERROR);
		// }
		// log.info("手机格式检验成功");
		// if(!ValidateLogin.validatePassword(vo.getPassword())){
		// return Result.ServerError(CodeMsg.PASSWORD_NULL_ERROR);
		// }
		// log.info("密码格式检验成功");
		// //登陆检查
		System.out.println(vo.getPassword());
		loginService.login(resp, vo);
		//校验成功
		return Result.success("success");

	}
}
