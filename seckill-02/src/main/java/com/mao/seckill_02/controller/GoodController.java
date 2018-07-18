package com.mao.seckill_02.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.mao.seckill_02.domain.OrderInfo;
import com.mao.seckill_02.domain.Product;
import com.mao.seckill_02.domain.ProductVo;
import com.mao.seckill_02.domain.SeckillOrder;
import com.mao.seckill_02.domain.SeckillProduct;
import com.mao.seckill_02.domain.User;
import com.mao.seckill_02.redis.RedisClient;
import com.mao.seckill_02.redis.RedisKey;
import com.mao.seckill_02.service.IUserService;
import com.mao.seckill_02.service.IOrderService;
import com.mao.seckill_02.service.IProductService;
import com.mao.seckill_02.service.ISeckillProductService;
import com.mao.seckill_02.service.ISeckillService;
import com.mao.seckill_02.service.impl.LoginServiceImpl;
import com.mao.seckill_02.util.CodeMsg;

@Controller
@RequestMapping("/goods")
public class GoodController {

	@Autowired
	private IUserService userService;
	@Autowired
	private IProductService productService;
	@Autowired
	private ISeckillService seckillService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ISeckillProductService seckillProductService;
	@Autowired
	private RedisClient redisClient;
	@Autowired
	private ThymeleafViewResolver thymeleafViewResolver;
	@Autowired
	private ApplicationContext applicationContext;

	// 第一种，复杂点的拿到User
	@RequestMapping("/toList1")
	public String toGoodList(HttpServletResponse resp, HttpServletRequest req, Model model,
			@CookieValue(value = LoginServiceImpl.COOKIE_TOKEN_KEY, required = false) String cookieValueS,
			@RequestParam(value = LoginServiceImpl.COOKIE_TOKEN_KEY, required = false) String cookieValueF) {// 手机客户端可能是放在参数中的
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println(cookie.getName());
		}
		// 设置优先级
		// 实际上为uuid
		String cookieValue = null;
		if (cookieValueF != null) {
			cookieValue = cookieValueS;
		} else if (cookieValueS != null) {
			cookieValue = cookieValueS;
		} else {
			// cookie不存在或者已经过期
			// 重新登陆
			return "login";
		}
		User u = this.userService.getValueFormCookies(resp, cookieValue, User.class);
		model.addAttribute("user", u);
		return "goods";
	}

	/**
	 * 直接在形参中注入User 秒杀商品列表 普通形式
	 * 
	 * @param resp
	 * @param req
	 * @param model
	 * @param u
	 * @return
	 * 
	 * 		@RequestMapping("/toList") public String
	 *         toGoodList(HttpServletResponse resp, HttpServletRequest req,Model
	 *         model, User u){//手机客户端可能是放在参数中的 if(u == null){ return "login"; }
	 *         model.addAttribute("user", u); List<ProductVo> allProductVo =
	 *         this.productService.getAllProductVo(); for (ProductVo productVo :
	 *         allProductVo) { System.out.println(productVo.toString());
	 *         System.out.println("productName:" + productVo.getProductName());
	 *         } model.addAttribute("productVoList", allProductVo); return
	 *         "goods"; }
	 */

	/**
	 * 直接在形参中注入User 
	 * 秒杀商品列表 通过缓存来做 把整个渲染过后的页面存入redis 返回整个页面 url缓存 对象缓存
	 * 
	 * @param resp
	 * @param req
	 * @param model
	 * @param u
	 * @return
	 */
	@RequestMapping(value = "/toList", produces = "text/html")
	@ResponseBody
	public String toGoodList(HttpServletResponse resp, HttpServletRequest req, Model model, User u) {// 手机客户端可能是放在参数中的
		if (u == null) {
			return null;
		}
		model.addAttribute("user", u);
		List<ProductVo> allProductVo = this.productService.getAllProductVo();
		model.addAttribute("productVoList", allProductVo);
		// 首先从缓存中取出模板页面
		RedisKey key = new RedisKey(60, "ProductListHtml");
		String html = redisClient.getFromRedis(key, "", String.class);
		// 如果有直接返回
		if (html != null) {
			return html;
		}
		// 如果为空，手动渲染
		SpringWebContext ctx = new SpringWebContext(req, resp, req.getServletContext(), req.getLocale(), model.asMap(),
				applicationContext);
		html = thymeleafViewResolver.getTemplateEngine().process("goods", ctx);
		redisClient.setToRedis(key, "", html);
		return html;
	}

	/**
	 * 商品详情页面
	 * 
	 * @param resp
	 * @param req
	 * @param model
	 * @param u
	 * @param productId
	 * @return
	 * 
	 * 		@RequestMapping("/detial/{productId}") public String
	 *         toGoodDetial(HttpServletResponse resp, HttpServletRequest
	 *         req,Model model, User u, @PathVariable("productId") Long
	 *         productId){//手机客户端可能是放在参数中的 if(u == null){ return "login"; }
	 *         model.addAttribute("user", u); ProductVo productVo =
	 *         this.productService.getProductVoByProductId(productId);
	 *         model.addAttribute("productVo", productVo); int seckillStatement
	 *         = this.productService.judgeTime(productVo);
	 *         model.addAttribute("seckillStatement", seckillStatement); Long
	 *         remainSeconds = productVo.getStartDate().getTime() -
	 *         System.currentTimeMillis(); if(seckillStatement == 0){
	 *         System.out.println(remainSeconds);
	 *         model.addAttribute("remainSeconds", remainSeconds/1000); }else{
	 *         model.addAttribute("remainSeconds",seckillStatement); } return
	 *         "productDetial"; }
	 */

	/**
	 * 商品详情页面:利用URL缓存
	 * 
	 * @param resp
	 * @param req
	 * @param model
	 * @param u
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/detial/{productId}", produces = "text/html")
	@ResponseBody
	public String toGoodDetial(HttpServletResponse resp, HttpServletRequest req, Model model, User u,
			@PathVariable("productId") Long productId) {// 手机客户端可能是放在参数中的
		if (u == null) {
			return "login";
		}
		RedisKey redisKey = new RedisKey(60, "ProductDetail");
		String html = redisClient.getFromRedis(redisKey, String.valueOf(productId), String.class);
		if (html != null) {
			return html;
		}
		model.addAttribute("user", u);
		ProductVo productVo = this.productService.getProductVoByProductId(productId);
		model.addAttribute("productVo", productVo);
		int seckillStatement = this.productService.judgeTime(productVo);
		model.addAttribute("seckillStatement", seckillStatement);
		Long remainSeconds = productVo.getStartDate().getTime() - System.currentTimeMillis();
		if (seckillStatement == 0) {
			model.addAttribute("remainSeconds", remainSeconds / 1000);
		} else {
			model.addAttribute("remainSeconds", seckillStatement);
		}
		// 如果为空，手动渲染
		SpringWebContext ctx = new SpringWebContext(req, resp, req.getServletContext(), req.getLocale(), model.asMap(),
				applicationContext);
		html = thymeleafViewResolver.getTemplateEngine().process("productDetial", ctx);
		redisClient.setToRedis(redisKey, String.valueOf(productId), String.class);
		return html;
	}

	/**
	 * 进行秒杀
	 * 
	 * @param resp
	 * @param req
	 * @param model
	 * @param u
	 * @param productId
	 * @return
	 */
	@RequestMapping("/seckill")
	public String doSeckill(HttpServletResponse resp, HttpServletRequest req, Model model, User u, Long productId) {// 手机客户端可能是放在参数中的
		if (u == null) {
			return "login";
		}
		model.addAttribute("user", u);
		System.out.println("seckill");
		// 首先判断时间，保险一点

		SeckillProduct sp = this.seckillProductService.getSeckillProductByProductId(productId);
		// ProductVo productVo =
		// this.productService.getProductVoByProductId(productId);
		// if(productVo == null){
		// model.addAttribute("errorMsg", CodeMsg.SERVER_ERROR);
		// return "seckill_fail";
		// }
		// this.seckillService.judgeSeckillSeconds(productVo);
		// 判断库存
		if (sp.getStockCount() <= 0) {// 库存不足
			// 不只可以用Ajax，直接设置数据也可以
			model.addAttribute("errorMsg", CodeMsg.STOCK_ERROR);
			return "seckill_fail";
		}
		// 判断用户是否秒杀成功，看看是否有秒杀成功的订单
		SeckillOrder seckillOrder = this.orderService.getSeckillOrderByUserIdAndProductId(u.getId(), productId);
		if (seckillOrder != null) {// 如果有，不能重复秒杀
			// 不只可以用Ajax，直接设置数据也可以
			model.addAttribute("errorMsg", CodeMsg.SECKILL_REPEAT_ERROR);
			return "seckill_fail";
		}
		// 秒杀事务
		OrderInfo orderInfo = this.seckillService.doSeckill(sp, u.getId());
		Product p = this.productService.getByProductId(productId);
		// 拿到秒杀订单
		// 跳转到秒杀订单页面
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("product", p);
		return "order";
	}

}
