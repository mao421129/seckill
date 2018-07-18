package com.mao.seckill_02.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.mao.seckill_02.domain.Product;
import com.mao.seckill_02.domain.ProductVo;

/**
 * 产品Mapper
 * 秒杀产品相关mapper
 * @author 71979
 *
 */
@Mapper
public interface ProductMapper {
	
	@Select("Select * from product where id = #{productId}")
	@Results({
		@Result(column="product_name", property="productName"),
		@Result(column="product_title", property="productTitle"),
		@Result(column="product_img", property="productImage"),
		@Result(column="product_detial", property="productDetial"),
		@Result(column="product_price", property="productPrice"),
		@Result(column="product_stock", property="productStock"),
	})
	Product getByProductId(Long productId);
	
	@Select("Select * from product")
	@Results({
		@Result(column="product_name", property="productName"),
		@Result(column="product_title", property="productTitle"),
		@Result(column="product_img", property="productImage"),
		@Result(column="product_detial", property="productDetial"),
		@Result(column="product_price", property="productPrice"),
		@Result(column="product_stock", property="productStock"),
	})
	List<Product> getAllProduct();
	
	@Select("Select p.*,s.seckill_price,s.stock_count,s.start_date,s.end_date from seckill_product as s left join product as p on s.product_id=p.id")
	@Results({
		@Result(column="product_name", property="productName"),
		@Result(column="product_title", property="productTitle"),
		@Result(column="product_img", property="productImage"),
		@Result(column="product_detial", property="productDetial"),
		@Result(column="product_price", property="productPrice"),
		@Result(column="product_stock", property="productStock"),
		@Result(column="seckill_price", property="seckillPrice"),
		@Result(column="stock_count", property="stockCount"),
		@Result(column="start_date", property="startDate"),
		@Result(column="end_date", property="endDate"),
	})
	List<ProductVo> getAllProductVo();
	
	@Select("Select p.*,s.seckill_price,s.stock_count,s.start_date,"
			+ "s.end_date from seckill_product as s left join product as p on p.id=s.product_id where s.id = #{productId}")
	@Results({
		@Result(column="product_name", property="productName"),
		@Result(column="product_title", property="productTitle"),
		@Result(column="product_img", property="productImage"),
		@Result(column="product_detial", property="productDetial"),
		@Result(column="product_price", property="productPrice"),
		@Result(column="product_stock", property="productStock"),
		@Result(column="seckill_price", property="seckillPrice"),
		@Result(column="stock_count", property="stockCount"),
		@Result(column="start_date", property="startDate"),
		@Result(column="end_date", property="endDate"),
	})
	ProductVo getProductVo(@Param("productId") Long productId);
}
