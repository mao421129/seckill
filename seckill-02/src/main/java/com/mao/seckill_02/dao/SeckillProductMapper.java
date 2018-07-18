package com.mao.seckill_02.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mao.seckill_02.domain.SeckillProduct;

@Mapper
public interface SeckillProductMapper {

	@Update("Update seckill_product set stock_count = stock_count - 1 where product_id = #{productId} and stock_count > 0")
	int updateStockCount(Long productId);

	@Select("Select * from seckill_product where product_id = #{productId}")
	@Results({
		@Result(column="product_id",property="productId"),
		@Result(column="seckill_price",property="seckillPrice"),
		@Result(column="stock_count",property="stockCount"),
		@Result(column="start_date",property="startDate"),
		@Result(column="end_date",property="endDate")
		})
	SeckillProduct getByProductId(Long productId);
	
	@Select("Select * from seckill_product")
	@Results({
		@Result(column="product_id",property="productId"),
		@Result(column="seckill_price",property="seckillPrice"),
		@Result(column="stock_count",property="stockCount"),
		@Result(column="start_date",property="startDate"),
		@Result(column="end_date",property="endDate")
		})
	List<SeckillProduct> getAll();
}
