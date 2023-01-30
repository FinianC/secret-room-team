package com.secret.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.mapper.ThemeMapper;
import com.secret.model.entity.ThemeEntity;
import com.secret.model.vo.ThemeVo;
import com.secret.service.ThemeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 恐怖等级 服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
@Service
public class ThemeServiceImpl extends ServiceImpl<ThemeMapper, ThemeEntity> implements ThemeService {

    @Resource
    private ThemeMapper themeMapper;

    @Override
    public List<ThemeVo> existsList() {
        return  themeMapper.existsThemeList();
    }
}
