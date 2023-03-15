package com.secret.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.entity.MyTicketEntity;
import com.secret.vo.applet.MyTicketQueryVo;
import com.secret.vo.applet.MyTicketVo;
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
    Page<MyTicketVo> page(@Param("page")Page<MyTicketVo> page, @Param("myTicketQueryVo") MyTicketQueryVo myTicketQueryVo, @Param("userId") Integer userId);

    /**
     * 详情
     * @param id
     * @param userId
     * @return
     */
    MyTicketVo detail(@Param("id") Integer id,@Param("userId") Integer userId);
}
