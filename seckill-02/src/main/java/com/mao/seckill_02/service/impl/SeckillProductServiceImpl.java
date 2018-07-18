package com.mao.seckill_02.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mao.seckill_02.dao.SeckillProductMapper;
import com.mao.seckill_02.domain.SeckillProduct;
import com.mao.seckill_02.service.ISeckillProductService;

/**
 * 处理秒杀商品操作
 * @author 71979
 *
 */
@Service("seckillProductService")
public class SeckillProductServiceImpl implements ISeckillProductService {

	@Autowired
	private SeckillProductMapper seckillProductMapper;
	
	@Transactional
	public boolean updateStockCount(Long productId) {
		int i = this.seckillProductMapper.updateStockCount(productId);
		if(i > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public SeckillProduct getSeckillProductByProductId(Long productId) {
		
		return seckillProductMapper.getByProductId(productId);
	}

	@Override
	public List<SeckillProduct> getAllSeckillProduct() {
		return this.seckillProductMapper.getAll();
	}
	
	

}
