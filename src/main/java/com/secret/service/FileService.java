package com.secret.service;

import com.secret.model.entity.FileEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.model.vo.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-20
 */
public interface FileService extends IService<FileEntity> {



    /**
     * 保存文件
     * @param multipartFiles
     * @return
     */
    R saveFile(MultipartFile[] multipartFiles);

}
