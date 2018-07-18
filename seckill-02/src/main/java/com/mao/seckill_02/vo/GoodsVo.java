package com.mao.seckill_02.vo;

import java.util.List;

import com.mao.seckill_02.domain.Product;
import com.mao.seckill_02.domain.ProductVo;
import com.mao.seckill_02.domain.User;

/**
 * 返回给页面ajax的数据
 * @author 71979
 *
 */
public class GoodsVo{


	private User u;
	private List<ProductVo> lists;
	
	
	public User getU() {
		return u;
	}
	public void setU(User u) {
		this.u = u;
	}
	public List<ProductVo> getLists() {
		return lists;
	}
	public void setLists(List<ProductVo> lists) {
		this.lists = lists;
	}

}
