package com.secret.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.model.entity.MotorcadeEntity;
import com.secret.mapper.MotorcadeMapper;
import com.secret.model.params.MotorcadeParam;
import com.secret.model.params.MotorcadeQueryParam;
import com.secret.model.vo.MotorcadeVo;
import com.secret.service.MotorcadeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 车队 服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
@Service
public class MotorcadeServiceImpl extends ServiceImpl<MotorcadeMapper, MotorcadeEntity> implements MotorcadeService {

    @Autowired
    private MotorcadeMapper motorcadeMapper;

    @Override
    public MotorcadeVo getMotorcadeVoById(Integer id) {
        MotorcadeVo motorcadeVoById = motorcadeMapper.getMotorcadeVoById(id);
        return motorcadeVoById;
    }

    @Override
    public Page<MotorcadeVo> getMotorcadeVoPage(MotorcadeQueryParam motorcadeQueryParam) {
        Page page = new Page<>(motorcadeQueryParam.getCurrent(), motorcadeQueryParam.getPageSize());
        Page<MotorcadeVo> motorcadeVoPage = motorcadeMapper.getMotorcadeVoPage(page, motorcadeQueryParam);
        return motorcadeVoPage;
    }
}
