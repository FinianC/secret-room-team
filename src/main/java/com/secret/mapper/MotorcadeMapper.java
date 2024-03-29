package com.secret.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.model.entity.MotorcadeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secret.model.params.MotorcadeQueryParam;
import com.secret.model.vo.MotorcadeVo;
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

     Page<MotorcadeVo> getMotorcadeVoPage(@Param("page") Page page, @Param("param")MotorcadeQueryParam motorcadeQueryParam,@Param("status") Integer status );

     MotorcadeVo getMotorcadeVo(@Param("id")Integer id);
}
