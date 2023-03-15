package com.secret.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.entity.TicketEntity;
import com.secret.params.applet.TicketQueryParam;
import com.secret.vo.applet.TicketVo;
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
    Page<TicketVo> page(@Param("page")Page<TicketEntity> page ,@Param("ticketQueryParam") TicketQueryParam ticketQueryParam);

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    TicketVo detailById(Integer id);

    /**
     * 增加库存
     * @param id
     * @return
     */
    Boolean increaseInventory(Integer id);
}
