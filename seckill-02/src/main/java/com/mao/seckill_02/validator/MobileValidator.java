package com.mao.seckill_02.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.mao.seckill_02.util.ValidateLogin;
/**
 * 判断手机号码格式是否正确类
 * 
 * @author 71979
 *
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String>{

	private boolean required = false;
	//初始化，拿到注解
	//相当于调用接口中的方法
	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}
	//value：实际需要判断的value,这里为手机号码1xxxxxxxxx
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required){//如果是必须的，判断格式是否正确
			return ValidateLogin.validateMobile(value);
		}else{//如果不是必须的，判断有没有值
			if(StringUtils.isEmpty(value)){
				//可以为空，所以空的情况下，返回true
				return true;
			}else{
				//如果不为空，还需要判断格式
				return ValidateLogin.validateMobile(value);
			}
		}
	}
}
