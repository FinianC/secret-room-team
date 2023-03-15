package com.secret.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.entity.MotorcadeEntity;
import com.secret.params.applet.MotorcadeQueryParam;
import com.secret.vo.applet.MotorcadeVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 车队 Mapper 接口
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
public interface MotorcadeMapper extends BaseMapper<MotorcadeEntity> {

     MotorcadeVo getMotorcadeVoById(Integer id);

     Page<MotorcadeVo> getMotorcadeVoPage(@Param("page") Page page, @Param("param") MotorcadeQueryParam motorcadeQueryParam, @Param("status") Integer status );

     MotorcadeVo getMotorcadeVo(@Param("id")Integer id);
}
