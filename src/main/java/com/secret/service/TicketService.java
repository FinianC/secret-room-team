package com.secret.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.secret.model.entity.TicketEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.model.params.TicketQueryParam;
import com.secret.model.params.purchaseTicketParam;
import com.secret.model.vo.R;
import com.secret.model.vo.TicketVo;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenDi
 * @since 2023-02-26
 */
public interface TicketService extends IService<TicketEntity> {


    /**
     * 分页查询票
     * @param ticketQueryParam
     * @return
     */
   Page<TicketVo> page( TicketQueryParam ticketQueryParam);


    /**
     * 密室详情
     * @param id
     * @return
     */
    TicketVo detailById(Integer id);

    /**
     * 购买票
     * @param purchaseTicketParam
     * @return
     */
    R<WxPayMpOrderResult> purchaseTicket(purchaseTicketParam purchaseTicketParam);

    /**
     * 支付
     * @param openId 用户openId
     * @param payNo 支付单号
     * @param payMoney 支付价格
     * @param productName 商品名称
     * @return
     */
    WxPayMpOrderResult pay(String openId, String payNo, String payMoney, String productName);

    /**
     * 退款
     * @param totalFee
     * @param payNum
     * @param refundNo
     * @param refundFee
     */
    void refund(String totalFee,String payNum,String refundNo,String refundFee);



    /**
     * 获取名称
     * @param id
     * @return
     */
    String getNameById(Integer id);

    Boolean increaseInventory(Integer id);


}
