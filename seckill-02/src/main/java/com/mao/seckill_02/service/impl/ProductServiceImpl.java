package com.mao.seckill_02.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mao.seckill_02.dao.ProductMapper;
import com.mao.seckill_02.domain.Product;
import com.mao.seckill_02.domain.ProductVo;
import com.mao.seckill_02.service.IProductService;

@Service("productService")
public class ProductServiceImpl implements IProductService{

	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public Product getByProductId(Long productId) {
		return this.productMapper.getByProductId(productId);
	}

	@Override
	public List<Product> getAllProduct() {
		return this.productMapper.getAllProduct();
	}

	public List<ProductVo> getAllProductVo(){
		return this.productMapper.getAllProductVo();
	}

	@Override
	public ProductVo getProductVoByProductId(Long productId) {
		// TODO Auto-generated method stub
		return this.productMapper.getProductVo(productId);
	}
	
	//判断秒杀时间
	// seckillState:0 未开始 1 正在经行 -1 已经结束
	public int judgeTime(ProductVo productVo) {
		long cur = System.currentTimeMillis();
		if(cur  < productVo.getStartDate().getTime()){//未开始
			return 0;
		}else if(cur > productVo.getEndDate().getTime()){//已经结束
			return -1;
		}
		return 1;//正在进行
	}


	public void reduseStockCount(Long productId) {
		
		
	}

	
	
}
