package com.secret.controller;


import com.secret.config.FileConfig;
import com.secret.constant.RS;
import com.secret.model.entity.FileEntity;
import com.secret.model.vo.FileVo;
import com.secret.model.vo.R;
import com.secret.service.FileService;
import com.secret.utils.TransferUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author wenxinji
 * @since 2022-10-19
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {



    @Resource
    private FileService fileService;

    @Autowired
    private FileConfig fileConfig;

    @Resource
    private HttpServletResponse response;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public @ResponseBody R<Integer> upload(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse httpServletResponse){
        if(!file.isEmpty()){
            // 如果目录不存在则创建
            httpServletResponse.setContentType("text/html;charset=utf-8");
            File uploadDir = new File(fileConfig.getFileUrl());
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String OriginalFilename = file.getOriginalFilename();//获取原文件名
            String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
            //重新随机生成名字
            String filename = UUID.randomUUID().toString() + suffixName;
            File localFile = new File(fileConfig.getFileUrl() + "/" + filename);
            try {
                file.transferTo(localFile); //把上传的文件保存至本地
                FileEntity fileEntity = new FileEntity();
                fileEntity.setName(OriginalFilename);
                fileEntity.setUrl(localFile.getPath());
                fileService.save(fileEntity);
                FileVo fileVo = new FileVo();
                TransferUtils.transferBean(fileEntity,fileVo);
                String url = fileConfig.getBaseUrl()+fileVo.getId();
                fileVo.setUrl(url);
                return R.success(fileVo);
            }catch (IOException e){
                e.printStackTrace();
                return R.fail(RS.UPLOAD_FOUNT);
            }
        }else{
            return R.fail(RS.FILE_NOT_FOUNT);
        }
    }

    @GetMapping(value = "/getFile/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation("获取图片-以ImageIO流形式写回")
    @ResponseBody
    public byte[] test(@PathVariable String id) throws Exception {
        FileEntity fileEntity = fileService.getById(id);
        File file = null;
        try {
//        读取图片
            if (fileEntity == null) {
                file = new File(getFileUrl() + id);
            } else {
                file = new File(fileEntity.getUrl());
            }
            if (!file.exists()) {
                log.error("file cannot be found {}", id);
                return null;
            }
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;

        } catch (IOException e) {
            log.error("获取图片异常{}",e.getMessage());
            return null;
        } finally {
        }
    }

    String getFileUrl(){
        if(StringUtils.isEmpty(fileConfig.getFileUrl())){
            return "/home/nginx/html/file/";
        }
        return fileConfig.getFileUrl().lastIndexOf("/")==fileConfig.getFileUrl().length()-1?fileConfig.getFileUrl():fileConfig.getFileUrl()+"/";
    }

}

