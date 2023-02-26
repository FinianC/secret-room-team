package com.secret.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.model.entity.TicketEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secret.model.params.TicketQueryParam;
import com.secret.model.vo.TicketVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenDi
 * @since 2023-02-26
 */
public interface TicketMapper extends BaseMapper<TicketEntity> {

    /**
     * 分页查询票
     * @param ticketQueryParam
     * @return
     */
    Page<TicketVo> page(@Param("page")Page page ,@Param("ticketQueryParam") TicketQueryParam ticketQueryParam);
}
