package com.mao.seckill_02.domain;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo {
	private Long id;
	private Long userId;
	private Long productId;
	private Long deliveryAddrId;
	private String productName;
	private int productCount;
	private BigDecimal productPrice;
	//pc端，手机端，mac端
	private int orderChannel;
	//0：未支付，1：已支付，2：已发货，3：已经收货，4，已完成，5，支付取消
	private int status;
	private Date creatDate;
	private Date payDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getDeliveryAddrId() {
		return deliveryAddrId;
	}
	public void setDeliveryAddrId(Long deliveryAddrId) {
		this.deliveryAddrId = deliveryAddrId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	public int getOrderChannel() {
		return orderChannel;
	}
	public void setOrderChannel(int orderChannel) {
		this.orderChannel = orderChannel;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreatDate() {
		return creatDate;
	}
	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
}
