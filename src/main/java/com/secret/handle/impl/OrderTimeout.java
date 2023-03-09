package com.secret.handle.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.secret.handle.RedisDelayQueueHandle;
import com.secret.model.constant.OrderPayConstant;
import com.secret.model.entity.MyTicketEntity;
import com.secret.model.entity.TicketEntity;
import com.secret.model.entity.TicketPayEntity;
import com.secret.model.enums.TicketStatusEnum;
import com.secret.service.MyTicketService;
import com.secret.service.TicketPayService;
import com.secret.service.TicketService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 订单支付超时处理类
 */
@Component
@Slf4j
public class OrderTimeout implements RedisDelayQueueHandle<Map<String,String>> {

	@Resource
	private MyTicketService myTicketService;

	@Resource
	private TicketPayService  ticketPayService;

	@Resource
	private TicketService ticketService;

	@Resource
	private WxPayService wxPayService;

	@Override
	@SneakyThrows
	@Transactional
	public void execute(Map<String,String> map) {
		//1.调用微信的支付接口，查询订单是否已经支付，如果确认没支付则，调用关闭订单支付的api,并修改订单的状态为关闭，同时回滚库存数量。
		// 2.如果支付状态为已支付则需要做补偿操作，修改订单的状态为已支付，订单历史记录
		log.info("(Receive the order delay message) {}", map);
		String orderId = map.get(OrderPayConstant.ORDER_ID);
		String payNo = map.get(OrderPayConstant.PAY_NO);
		TicketPayEntity ticketPayEntity = ticketPayService.getOne(new LambdaQueryWrapper<TicketPayEntity>().eq(TicketPayEntity::getPayNum,payNo));
		// 当前支付单号已被替换
		if(ticketPayEntity == null ) return;
		MyTicketEntity myTicketEntity = myTicketService.getById(orderId);

		WxPayOrderQueryRequest wxPayOrderQueryRequest = new WxPayOrderQueryRequest();
		wxPayOrderQueryRequest.setOutTradeNo(ticketPayEntity.getPayNum());
		// 查询当前订单是否成功支付
		WxPayOrderQueryResult wxPayOrderQueryResult = wxPayService.queryOrder(wxPayOrderQueryRequest);
		log.info( "wx pay order query result {} payNo {}",wxPayOrderQueryResult.getReturnCode(),ticketPayEntity.getPayNum() );
		log.info( "trade state  {}",wxPayOrderQueryResult.getTradeState() );
		// 未支付
		if("NOTPAY".equals(wxPayOrderQueryResult.getTradeState()) ){
			myTicketEntity.setStatus(TicketStatusEnum.PAYMENT_CLOSE.getCode());
			ticketPayEntity.setStatus(TicketStatusEnum.PAYMENT_CLOSE.getCode());
			myTicketService.updateById(myTicketEntity);
			ticketPayService.updateById(ticketPayEntity);
			ticketService.increaseInventory(myTicketEntity.getTicketId());
			return;
		}
		// 支付成功并且 状态为成功 直接退出
		if(myTicketEntity.getStatus().equals(TicketStatusEnum.PAYMENT_SUCCEEDED.getCode())){
			return;
		}
		// 支付成功 但状态未修改
		myTicketEntity.setStatus(TicketStatusEnum.PAYMENT_SUCCEEDED.getCode());
		ticketPayEntity.setStatus(TicketStatusEnum.PAYMENT_SUCCEEDED.getCode());
		myTicketService.updateById(myTicketEntity);
		ticketPayService.updateById(ticketPayEntity);
	}
}
