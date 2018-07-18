package com.mao.seckill_02.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 包含了商品信息和秒杀相关信息
 * @author 71979
 *
 */
public class ProductVo extends Product{

	//秒杀价格
	private BigDecimal seckillPrice;
	//秒杀的数量
	private int stockCount;
	private Date startDate;
	private Date endDate;
	
	public BigDecimal getSeckillPrice() {
		return seckillPrice;
	}
	public void setSeckillPrice(BigDecimal seckillPrice) {
		this.seckillPrice = seckillPrice;
	}
	public int getStockCount() {
		return stockCount;
	}
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "ProductVo [seckillPrice=" + seckillPrice + ", stockCount=" + stockCount + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
	
}
