package com.mao.seckill_02.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mao.seckill_02.domain.ProductVo;
import com.mao.seckill_02.domain.SeckillMessage;
import com.mao.seckill_02.domain.SeckillOrder;
import com.mao.seckill_02.domain.SeckillProduct;
import com.mao.seckill_02.domain.User;
import com.mao.seckill_02.rabbitMq.RabbitMQReceiver;
import com.mao.seckill_02.rabbitMq.RabbitMQSender;
import com.mao.seckill_02.redis.RedisClient;
import com.mao.seckill_02.redis.RedisKey;
import com.mao.seckill_02.service.IOrderService;
import com.mao.seckill_02.service.IProductService;
import com.mao.seckill_02.service.ISeckillProductService;
import com.mao.seckill_02.service.ISeckillService;
import com.mao.seckill_02.service.IUserService;
import com.mao.seckill_02.util.CodeMsg;
import com.mao.seckill_02.util.MD5Utils;
import com.mao.seckill_02.util.Result;
import com.mao.seckill_02.util.UUIDUtil;
import com.mao.seckill_02.vo.GoodsVo;
import com.mao.seckill_02.vo.ProductDetialVo;

/**
 * 商品列表页 前后端分离
 * 
 * @author 71979
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController implements InitializingBean{

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
	private RabbitMQSender mqSender;
	@Autowired
	private RabbitMQReceiver mqReceiver;
	private static Logger log = org.slf4j.LoggerFactory.getLogger(ProductController.class);
	private static Map<Long, Boolean> markmap = new HashMap<>();
	/**
	 * 直接在形参中注入User 秒杀商品列表 前后端分离
	 * 
	 * @param resp
	 * @param req
	 * @param model
	 * @param u
	 * @return
	 */
	@RequestMapping(value = "/toList")
	@ResponseBody
	public Result<GoodsVo> toGoodList(HttpServletResponse resp, HttpServletRequest req, Model model, User u) {
		if (u == null) {
			return Result.ServerError(CodeMsg.USER_ERROR);
		}
		List<ProductVo> allProductVo = this.productService.getAllProductVo();
		GoodsVo gv = new GoodsVo();
		gv.setU(u);
		gv.setLists(allProductVo);
		return Result.success(gv);
	}

	/**
	 * 会被回调，做一些初始化的操作
	 * 会不会每次都会执行一次这个接口?
	 */
	public void afterPropertiesSet() throws Exception {
		List<SeckillProduct> allSeckillProduct = 
				seckillProductService.getAllSeckillProduct();
		if(allSeckillProduct.size() > 0){
			for (SeckillProduct seckillProduct : allSeckillProduct) {
				int stock = seckillProduct.getStockCount();
				String key = String.valueOf(seckillProduct.getProductId());
				redisClient.setToRedis(RedisKey.preKeyOfSeckillProductStock, key, stock);
				markmap.put(seckillProduct.getProductId(), false);
			}
		}
	}

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
	@RequestMapping(value = "/detial")
	@ResponseBody
	public Result<ProductDetialVo> toGoodDetial(HttpServletResponse resp, HttpServletRequest req, User u,
			Long productId) {// 手机客户端可能是放在参数中的
		if (u == null) {
			return Result.ServerError(CodeMsg.USER_ERROR);
		}

		ProductVo productVo = this.productService.getProductVoByProductId(productId);
		if (productVo == null) {
			return Result.ServerError(CodeMsg.SERVER_ERROR);
		}
		ProductDetialVo pv = new ProductDetialVo();
		int seckillStatement = this.productService.judgeTime(productVo);
		Long remainSeconds = (productVo.getStartDate().getTime() - System.currentTimeMillis()) / 1000;
		if (seckillStatement == 0) {
			pv.setRemainSeconds(remainSeconds);
		} else {
			pv.setRemainSeconds((long) seckillStatement);
		}
		pv.setProductVo(productVo);
		pv.setSeckillStatement(seckillStatement);
		pv.setUser(u);
		return Result.success(pv);
	}

	@RequestMapping("/getSeckillPromit")
	@ResponseBody
	public Result<String> getSeckillPromit(HttpServletResponse resp, HttpServletRequest req, Model model, User u,
			@RequestParam("productId") Long productId){
		log.error("getSeckillPromit");
		if(u == null){
			return Result.ServerError(CodeMsg.USER_ERROR);
		}
		if(productId == null){
			log.info("productId is null");
			return Result.ServerError(CodeMsg.SERVER_ERROR);
		}
		String path = this.seckillService.firtPath(u.getId(), productId);

		return Result.success(path);
	}
	
	/**
	 * 秒杀
	 * 
	 * @param resp
	 * @param req
	 * @param model
	 * @param u
	 * @param productId
	 * @return
	 */
	@RequestMapping("/{path}/seckill")
	@ResponseBody
	public Result<String> doSeckill(HttpServletResponse resp, HttpServletRequest req, Model model, User u,
			@RequestParam("productId") Long productId,@PathVariable("path") String path) {// 手机客户端可能是放在参数中的

		if (u == null) {
			return Result.ServerError(CodeMsg.USER_ERROR);
		}
		boolean pass = this.seckillService.validatePath(u.getId(), productId, path);
		if(!pass){
			return Result.ServerError(CodeMsg.OPERATION_ERROR);
		}
		// 1 首先判断时间，保险一点,这里没做--------------------------------------------
		RedisKey prekey = RedisKey.preKeyOfSeckillProductStock;
		// 2 减少库存，从redis中取库存------------------------------------------------
		//优化：redis会减到负数，虽然无影响，但是会消耗资源
		Boolean mark = this.markmap.get(productId);
		if(mark){
			return Result.ServerError(CodeMsg.STOCK_ERROR);
		}
		
		Long decr = redisClient.decr(prekey.getPrefix(), String.valueOf(productId));
		if(decr < 0){
			this.markmap.put(productId,true);
			return Result.ServerError(CodeMsg.STOCK_ERROR);
		}
		// 3 判断用户是否秒杀成功，看看是否有秒杀成功的订单---------------------------------
		SeckillOrder seckillOrder = this.orderService.getSeckillOrderByUserIdAndProductId(u.getId(), productId);
		if (seckillOrder != null) {// 如果有，不能重复秒杀
			return Result.ServerError(CodeMsg.SECKILL_REPEAT_ERROR);
		}
		// 4 秒杀 -------------------------------------------------------------
		/*SeckillProduct sp = this.seckillProductService.getSeckillProductByProductId(productId);
		if(sp == null){
			return Result.ServerError(CodeMsg.SERVER_ERROR);
		}
		OrderInfo orderInfo = this.seckillService.doSeckill(sp, u.getId());
		if (orderInfo == null) {
			return Result.ServerError(CodeMsg.SECKILL_ERROR);
		}*/
		//4 秒杀，入队，异步进行--------------------------------------------
		SeckillMessage sm = new SeckillMessage();
		sm.setUser(u);
		sm.setProductId(productId);
		String message = RedisClient.beanToJson(sm);
		mqSender.send(message);
		// 5 成功无异常，则返回
		return Result.success("0");
	}
	/**
	 * 
	 * @param resp
	 * @param req
	 * @param model
	 * @param u
	 * @param productId
	 * @return success:orderId 成功
	 * @return fail:-1 失败
	 * @return zore:0继续排队
	 */
	@RequestMapping(value="/result",method=RequestMethod.GET)
	@ResponseBody
	public Result<Long> getOrder(HttpServletResponse resp, HttpServletRequest req, 
			Model model, User u,@RequestParam("productId") Long productId){
		if(u == null){
			return Result.ServerError(CodeMsg.USER_ERROR);
		}
		//OrderInfo orderInfo = redisClient.getFromRedis(RedisKey.orderInfoPreKey, u.getId() 
		//		+ "_" + productId, OrderInfo.class);
		Long result = seckillService.getSecKillResult(u.getId(), productId);
		System.out.println("result:" + result + "");
		return Result.success(result);
	}
}
