package com.secret.service;

import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.secret.model.entity.MyTicketEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.model.params.RefundParam;
import com.secret.model.params.ToPayParam;
import com.secret.model.params.purchaseTicketParam;
import com.secret.model.vo.R;
import org.springframework.web.bind.annotation.RequestBody;

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

}
