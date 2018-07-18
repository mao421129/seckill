package com.mao.seckill_02.vo;

import com.mao.seckill_02.domain.ProductVo;
import com.mao.seckill_02.domain.User;

public class ProductDetialVo {

	private long remainSeconds;
	private int seckillStatement;
	private User user;
	private ProductVo productVo;
	public long getRemainSeconds() {
		return remainSeconds;
	}
	public void setRemainSeconds(Long remainSeconds2) {
		this.remainSeconds = remainSeconds2;
	}
	public int getSeckillStatement() {
		return seckillStatement;
	}
	public void setSeckillStatement(int seckillStatement) {
		this.seckillStatement = seckillStatement;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ProductVo getProductVo() {
		return productVo;
	}
	public void setProductVo(ProductVo productVo) {
		this.productVo = productVo;
	}
	
	
}
