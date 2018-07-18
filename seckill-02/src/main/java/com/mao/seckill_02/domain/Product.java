package com.mao.seckill_02.domain;

import java.math.BigDecimal;

public class Product {

	private Long id;
	private String productName;
	private String productTitle;
	private String productImage;
	private String productDetial;
	//产品原价
	private BigDecimal productPrice;
	private int productStock;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getProductDetial() {
		return productDetial;
	}
	public void setProductDetial(String productDetial) {
		this.productDetial = productDetial;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	public int getProductStock() {
		return productStock;
	}
	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", productTitle=" + productTitle
				+ ", productImage=" + productImage + ", productDetial=" + productDetial + ", productPrice="
				+ productPrice + ", productStock=" + productStock + "]";
	}
}
