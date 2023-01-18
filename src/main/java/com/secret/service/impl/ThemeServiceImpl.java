package com.secret.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.mapper.TerrorLevelMapper;
import com.secret.mapper.ThemeMapper;
import com.secret.model.entity.TerrorLevelEntity;
import com.secret.model.entity.ThemeEntity;
import com.secret.service.TerrorLevelService;
import com.secret.service.ThemeService;
import org.springframework.stereotype.Service;

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

}
