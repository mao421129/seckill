package com.mao.seckill_02.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.mao.seckill_02.domain.SeckillOrder;

@Mapper
public interface SeckillOrderMapper {

	@Select("Select * from seckill_order where user_id = #{userId} and product_id=#{productId}")
	@Results({
		@Result(column="user_id", property="userId"),
		@Result(column="order_id", property="orderId"),
		@Result(column="product_id", property="productId"),
			})
	SeckillOrder getByUserIdAndProductId(@Param("userId")Long userId, @Param("productId")Long productId);
	
	@Insert("Insert into seckill_order(user_id,order_id,product_id) "
			+ "values(#{userId}, #{orderId},#{productId})")
	int add(SeckillOrder seckillOrder);
}
