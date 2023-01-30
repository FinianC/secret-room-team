package com.secret.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secret.model.entity.TerrorLevelEntity;
import com.secret.model.entity.ThemeEntity;
import com.secret.model.vo.ThemeVo;

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
