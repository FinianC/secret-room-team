package com.secret.service.impl;

import com.secret.constant.RS;
import com.secret.model.entity.FileEntity;
import com.secret.mapper.FileMapper;
import com.secret.model.vo.FileVo;
import com.secret.model.vo.R;
import com.secret.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.utils.TransferUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Value("${file.upload.path}")
    public String uploadPath;

    /**
     * 保存文件
     *
     * @param multipartFiles
     * @return
     */
    @Override
    public R<List<Integer>> saveFile(MultipartFile[] multipartFiles) {
        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        List<Integer> list = new ArrayList<>();
        for ( MultipartFile file : multipartFiles ){
            String OriginalFilename = file.getOriginalFilename();//获取原文件名
            String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
            //重新随机生成名字
            String filename = UUID.randomUUID().toString() + suffixName;
            File localFile = new File(uploadPath + "/" + filename);
            try {
                file.transferTo(localFile); //把上传的文件保存至本地
                FileEntity fileEntity = new FileEntity();
                fileEntity.setName(OriginalFilename);
                fileEntity.setUrl(localFile.getPath());
                save(fileEntity);
                list.add(fileEntity.getId());
            }catch (IOException e){
                e.printStackTrace();
                return R.fail(RS.UPLOAD_FOUNT);
            }
        }
        return R.success(list);
    }


}
