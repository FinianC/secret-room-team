package com.secret.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.entity.ThemeEntity;
import com.secret.vo.applet.ThemeVo;

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
