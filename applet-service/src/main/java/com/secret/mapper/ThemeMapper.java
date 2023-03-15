package com.secret.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secret.entity.ThemeEntity;
import com.secret.vo.applet.ThemeVo;

import java.util.List;

/**
 * <p>
 * 恐怖等级 Mapper 接口
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
public interface ThemeMapper extends BaseMapper<ThemeEntity> {

    List<ThemeVo> existsThemeList();

}
