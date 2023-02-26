package com.secret.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.model.entity.TicketEntity;
import com.secret.mapper.TicketMapper;
import com.secret.model.params.TicketQueryParam;
import com.secret.model.vo.TicketVo;
import com.secret.service.TicketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2023-02-26
 */
@Service
public class TicketServiceImpl extends ServiceImpl<TicketMapper, TicketEntity> implements TicketService {

    @Resource
    private TicketMapper ticketMapper;

    @Override
    public Page<TicketVo> page(TicketQueryParam ticketQueryParam) {
        Page<TicketVo> page = ticketMapper.page(new Page(ticketQueryParam.getCurrent(), ticketQueryParam.getPageSize()), ticketQueryParam);
        return page;
    }
}
