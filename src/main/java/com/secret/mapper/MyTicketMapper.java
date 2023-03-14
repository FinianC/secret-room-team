package com.secret.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.model.entity.MyTicketEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secret.model.vo.MyTicketQueryVo;
import com.secret.model.vo.MyTicketVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenDi
 * @since 2023-03-04
 */
public interface MyTicketMapper extends BaseMapper<MyTicketEntity> {

    /**
     * 查询的我的密室票
     * @param page
     * @param myTicketQueryVo
     * @return
     */
    Page<MyTicketVo> page(@Param("page")Page<MyTicketVo> page,@Param("myTicketQueryVo") MyTicketQueryVo myTicketQueryVo,@Param("userId") Integer userId);
}
