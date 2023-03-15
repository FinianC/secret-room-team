package com.secret.dto.applet;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("消息通知")
public class wxsmallTemplate {

    // 消息接收方
    private String toUser;
    // 模板id
    private String templateId;
    // 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转
    private String page;

    // 模板内容字体的颜色，不填默认黑色 【废弃】
    private String color;
    // 模板需要放大的关键词，不填则默认无放大
    private String emphasis_keyword;

}
