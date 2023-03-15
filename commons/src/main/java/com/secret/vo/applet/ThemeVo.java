package com.secret.vo.applet;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @program: secret-room
 * @description: 主题vo
 * @author: 陈迪
 * @create: 2023/01/18 22:42
 */
@Data
@ApiModel("主题vo")
public class ThemeVo {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

}
