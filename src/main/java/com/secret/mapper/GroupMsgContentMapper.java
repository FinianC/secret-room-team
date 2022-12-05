package com.secret.mapper;

import com.secret.model.entity.GroupMsgContentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 群聊消息 Mapper 接口
 * </p>
 *
 * @author chenDi
 * @since 2022-12-04
 */
public interface GroupMsgContentMapper extends BaseMapper<GroupMsgContentEntity> {

     Integer getMaxIdByMotorcadeId(@Param("motorcadeId") Integer motorcadeId);

}
