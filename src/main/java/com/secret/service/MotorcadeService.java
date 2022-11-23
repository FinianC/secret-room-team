package com.secret.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.model.entity.MotorcadeEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.model.params.MotorcadeParam;
import com.secret.model.params.MotorcadeQueryParam;
import com.secret.model.vo.MotorcadeVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 车队 服务类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
public interface MotorcadeService extends IService<MotorcadeEntity> {

    MotorcadeVo getMotorcadeVoById(Integer id);

    Page<MotorcadeVo> getMotorcadeVoPage(MotorcadeQueryParam motorcadeQueryParam);

    MotorcadeVo getMotorcadeVo(Integer id);

}
