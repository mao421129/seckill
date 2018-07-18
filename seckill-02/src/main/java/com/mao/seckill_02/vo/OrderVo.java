package com.mao.seckill_02.vo;

import com.mao.seckill_02.domain.OrderInfo;
import com.mao.seckill_02.domain.Product;
import com.mao.seckill_02.domain.SeckillOrder;
import com.mao.seckill_02.domain.User;

public class OrderVo {

	private User user;
	private Product product;
	private OrderInfo orderInfo;
	private SeckillOrder seckillOrder;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
	public SeckillOrder getSeckillOrder() {
		return seckillOrder;
	}
	public void setSeckillOrder(SeckillOrder seckillOrder) {
		this.seckillOrder = seckillOrder;
	}
}
