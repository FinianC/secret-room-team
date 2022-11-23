package com.secret.service.impl;

import com.secret.model.entity.FileEntity;
import com.secret.mapper.FileMapper;
import com.secret.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-20
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService {

}
