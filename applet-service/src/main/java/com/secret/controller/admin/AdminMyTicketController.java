package com.secret.controller.admin;


import com.secret.params.applet.RefundParam;
import com.secret.service.MyTicketService;
import com.secret.vo.R;
import io.swagger.annotations.ApiOperation;
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

