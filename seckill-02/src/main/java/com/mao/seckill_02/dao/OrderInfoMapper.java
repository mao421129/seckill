package com.mao.seckill_02.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.mao.seckill_02.domain.OrderInfo;

/**
 * 订单详情mapper
 * @author 71979
 *
 */
@Mapper
public interface OrderInfoMapper {

	@Insert("INSERT into order_info(user_id,product_id,delivery_addr_id,product_name,product_count,product_price,order_channel"
			+ ",status,create_date,pay_date) values(#{userId},#{productId},#{deliveryAddrId},#{productName}"
			+ ",#{productCount},#{productPrice},#{orderChannel},#{status},#{creatDate},#{payDate})")
	@SelectKey(keyColumn="id",keyProperty="id",resultType=long.class,before=false,statement="select LAST_INSERT_ID()")
	public long add(OrderInfo orderInfo);
	
	@Select("Select * from order_info where id = #{id}")
	@Results({
		@Result(column="user_id", property="userId"),
		@Result(column="product_id", property="productId"),
		@Result(column="delivery_addr_id", property="deliveryAddrId"),
		@Result(column="product_name", property="productName"),
		@Result(column="product_count", property="productCount"),
		@Result(column="product_price", property="productPrice"),
		@Result(column="order_channel", property="orderChannel"),
		@Result(column="status", property="status"),
		@Result(column="create_date", property="creatDate"),
		@Result(column="pay_date", property="payDate")
	})
	public OrderInfo select(long id);
}
