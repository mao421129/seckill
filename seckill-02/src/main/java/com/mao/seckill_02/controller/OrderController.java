package com.mao.seckill_02.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mao.seckill_02.dao.OrderInfoMapper;
import com.mao.seckill_02.dao.ProductMapper;
import com.mao.seckill_02.dao.SeckillOrderMapper;
import com.mao.seckill_02.domain.OrderInfo;
import com.mao.seckill_02.domain.Product;
import com.mao.seckill_02.domain.User;
import com.mao.seckill_02.util.CodeMsg;
import com.mao.seckill_02.util.Result;
import com.mao.seckill_02.vo.OrderVo;

/**
 * 订单相关
 * @author 71979
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private SeckillOrderMapper seckillOrderMapper;
	@Autowired
	private ProductMapper productMapper;
	@RequestMapping("/detail")
	@ResponseBody
	public Result<OrderVo> getOrderDetial(HttpServletResponse resp, HttpServletRequest req, User u, Long orderId){
		System.out.println("orderId:" + orderId);
		OrderInfo orderInfo = this.orderInfoMapper.select(orderId);
		if(orderInfo == null){
			return Result.ServerError(CodeMsg.PRODUCTSERVICE_ERROR);
		}
		Product p = this.productMapper.getByProductId(orderInfo.getProductId());
		if(p == null){
			return Result.ServerError(CodeMsg.PRODUCTSERVICE_ERROR);
		}
		OrderVo vo = new OrderVo();
		vo.setOrderInfo(orderInfo);
		vo.setProduct(p);
		vo.setUser(u);
		return Result.success(vo);
	}
}
