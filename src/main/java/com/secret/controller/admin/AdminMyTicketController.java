package com.secret.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.secret.model.entity.MyTicketEntity;
import com.secret.model.entity.RefundRecordEntity;
import com.secret.model.entity.TicketPayEntity;
import com.secret.model.enums.RefundStatusEnum;
import com.secret.model.params.RefundParam;
import com.secret.model.params.ToPayParam;
import com.secret.model.vo.R;
import com.secret.service.MyTicketService;
import com.secret.service.RefundRecordService;
import com.secret.service.TicketPayService;
import com.secret.service.TicketService;
import com.secret.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.cms.PasswordRecipientId;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenDi
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/admin/myTicket")
public class AdminMyTicketController {


    @Resource
    private MyTicketService myTicketService;


    @ApiOperation(value = "退款", httpMethod = "POST")
    @PostMapping("/refund")
    @Transactional
    public R<Boolean> refund(@RequestBody RefundParam refundParam) {
        return  myTicketService.refund(refundParam);
    }
}

