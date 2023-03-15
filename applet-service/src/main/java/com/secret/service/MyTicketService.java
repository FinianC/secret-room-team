package com.secret.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.secret.entity.MyTicketEntity;
import com.secret.params.applet.RefundParam;
import com.secret.params.applet.ToPayParam;
import com.secret.vo.R;
import com.secret.vo.applet.MyTicketQueryVo;
import com.secret.vo.applet.MyTicketVo;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenDi
 * @since 2023-03-04
 */
public interface MyTicketService extends IService<MyTicketEntity> {

    /**
     * 去支付
     * @param toPayParam
     * @return
     */
    R<WxPayMpOrderResult> toPay(ToPayParam toPayParam);


    /**
     * 退款
     * @param refundParam
     * @return
     */
    R<Boolean> refund( RefundParam refundParam);

    /**
     * 分页查询我的密室票
     * @param myTicketQueryVo
     * @return
     */
    Page<MyTicketVo> page(MyTicketQueryVo myTicketQueryVo);


    /**
     * 详情
     * @param id
     * @return
     */
    MyTicketVo detail(Integer id);

    /**
     * 获取消费二维码
     * @param orderId
     * @param response
     */
    void getQRCode( Integer orderId, HttpServletResponse response );

}
