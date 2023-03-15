package com.secret.dto.applet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDataDTO {

    /** 模板内容字体的颜色，不填默认黑色.*/
    private String color;

    /** 模板需要放大的关键词，不填则默认无放大.*/
    private String value;
}
