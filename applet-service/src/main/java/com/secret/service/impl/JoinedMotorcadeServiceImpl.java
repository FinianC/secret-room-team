package com.secret.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.constant.RS;
import com.secret.dto.applet.FleetChangesEventMessage;
import com.secret.entity.GroupChatEntity;
import com.secret.entity.JoinedMotorcadeEntity;
import com.secret.enums.applet.FleetChangesEnum;
import com.secret.event.producer.FleetChangesEventPublisher;
import com.secret.mapper.JoinedMotorcadeMapper;
import com.secret.service.GroupChatMemberService;
import com.secret.service.GroupChatService;
import com.secret.service.JoinedMotorcadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * 已加入的车队 服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
@Service
public class JoinedMotorcadeServiceImpl extends ServiceImpl<JoinedMotorcadeMapper, JoinedMotorcadeEntity> implements JoinedMotorcadeService {

    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private GroupChatMemberService groupChatMemberService;

    @Autowired
    private FleetChangesEventPublisher fleetChangesEventPublisher;

    /**
     * 离开车队
     *
     * @param userId
     * @param motorcadeId
     * @return
     */
    @Override
    public Boolean leave(Integer userId, Integer motorcadeId) {

        boolean remove = remove(new LambdaQueryWrapper<JoinedMotorcadeEntity>()
                .eq(JoinedMotorcadeEntity::getMotorcadeId, motorcadeId)
                .eq(JoinedMotorcadeEntity::getUserId, userId));
        Assert.isTrue(remove, RS.MEMBER_NOT_FOUND.message());
        GroupChatEntity one = groupChatService.getOne(new LambdaQueryWrapper<GroupChatEntity>()
                .select(GroupChatEntity::getId)
                .eq(GroupChatEntity::getMotorcadeId, motorcadeId));
        // 离开 群聊
        groupChatMemberService.leaveGroupChat(userId,one.getId());

        fleetChangesEventPublisher.publish(new FleetChangesEventMessage(
                FleetChangesEnum.LEAVE.getCode()
                ,motorcadeId
                ,userId));
        return null;
    }

    /**
     * 加入车队
     *
     * @param userId
     * @param motorcadeId
     * @return
     */
    @Override
    public JoinedMotorcadeEntity join(Integer userId, Integer motorcadeId) {
        JoinedMotorcadeEntity joinedMotorcadeEntity = new JoinedMotorcadeEntity();
        joinedMotorcadeEntity.setMotorcadeId(motorcadeId);
        joinedMotorcadeEntity.setUserId(userId);
        save(joinedMotorcadeEntity);
        return  joinedMotorcadeEntity;
    }
}
