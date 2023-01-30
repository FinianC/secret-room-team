package com.secret.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.model.entity.ThemeEntity;
import com.secret.model.vo.ThemeVo;

import java.util.List;

/**
 * <p>
 * 恐怖等级 服务类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
public interface ThemeService extends IService<ThemeEntity> {

    /**
     * 已存在主题
     */
    List<ThemeVo> existsList();

}
