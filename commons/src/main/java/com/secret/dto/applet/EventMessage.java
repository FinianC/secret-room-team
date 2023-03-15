package com.secret.dto.applet;

import lombok.Data;

/**
 * 事件消息
 * @param <T>
 */
@Data
public class EventMessage<T> {
    /**
     * 处理类
     */
    private String handle;

    /**
     * 数据
     */
    private T data;

}
