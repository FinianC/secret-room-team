package com.secret.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.model.entity.TicketEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.model.params.TicketQueryParam;
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

}
