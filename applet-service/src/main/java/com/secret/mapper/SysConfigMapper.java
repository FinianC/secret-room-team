package com.secret.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secret.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenDi
 * @since 2023-03-19
 */
public interface SysConfigMapper extends BaseMapper<SysConfigEntity> {


    String getOrderTimeout(@Param("variable") String variable);

}
