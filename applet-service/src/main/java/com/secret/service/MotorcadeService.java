package com.secret.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.entity.MotorcadeEntity;
import com.secret.params.applet.MotorcadeQueryParam;
import com.secret.vo.applet.ChatListVo;
import com.secret.vo.applet.MotorcadeVo;

import java.util.List;

/**
 * <p>
 * 车队 服务类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
public interface MotorcadeService extends IService<MotorcadeEntity> {

    MotorcadeVo getMotorcadeVoById(Integer id);

    Page<MotorcadeVo> getMotorcadeVoPage(MotorcadeQueryParam motorcadeQueryParam);

    MotorcadeVo getMotorcadeVo(Integer id);

    /**
     * 获取聊天列表
     * @param userId
     * @return
     */
    List<ChatListVo> getChatListVo(Integer userId);

}
