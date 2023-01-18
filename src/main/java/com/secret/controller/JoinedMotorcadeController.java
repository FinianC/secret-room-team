package com.secret.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.constant.RS;
import com.secret.event.FleetChangesEvent;
import com.secret.event.producer.FleetChangesEventPublisher;
import com.secret.model.dto.FleetChangesEventMessage;
import com.secret.model.entity.*;
import com.secret.model.enums.FleetChangesEnum;
import com.secret.model.params.JoinedMotorcadeParam;
import com.secret.model.vo.MotorcadeVo;
import com.secret.model.vo.R;
import com.secret.model.vo.UserVerificationVo;
import com.secret.model.vo.UserVo;
import com.secret.service.*;
import com.secret.utils.UserLoginUtils;
import com.secret.utils.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 已加入的车队 前端控制器
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
@RestController
@RequestMapping("/user/joinedMotorcade")
public class JoinedMotorcadeController {

    @Autowired
    private MotorcadeService motorcadeService;

    @Autowired
    private JoinedMotorcadeService joinedMotorcadeService;

    @Autowired
    private FleetChangesEventPublisher fleetChangesEventPublisher;

    @Autowired
    private GroupMsgContentService groupMsgContentService;

    @Autowired
    private GroupChatMemberService groupChatMemberService;

    @Autowired
    private GroupChatService groupChatService;



    @ApiOperation(value = "加入车队", httpMethod = "POST")
    @PostMapping("/join")
    @Transactional
    public R join(@RequestBody JoinedMotorcadeParam joinedMotorcadeParam) {

        UserVo user =(UserVo) UserLoginUtils.getUserInfo().getUser();
        JoinedMotorcadeEntity joinedMotorcadeEntity = joinedMotorcadeService.getOne(new LambdaQueryWrapper<JoinedMotorcadeEntity>()
                .eq(JoinedMotorcadeEntity::getMotorcadeId, joinedMotorcadeParam.getMotorcadeId())
                .eq(JoinedMotorcadeEntity::getUserId, user.getId()));
        Assert.isNull(joinedMotorcadeEntity, RS.DO_NOT_JOIN_AGAIN.message());

        GroupChatEntity one = groupChatService.getOne(new LambdaQueryWrapper<GroupChatEntity>()
                .select(GroupChatEntity::getId)
                .eq(GroupChatEntity::getMotorcadeId, joinedMotorcadeParam.getMotorcadeId()));

        // 获取加入时收到的第一次消息id
        Integer maxIdByGroupId = groupMsgContentService.getMaxIdByMotorcadeId(one.getId());
        joinedMotorcadeEntity = new JoinedMotorcadeEntity();
        joinedMotorcadeEntity.setMotorcadeId(joinedMotorcadeParam.getMotorcadeId());
        joinedMotorcadeEntity.setUserId(user.getId());
        joinedMotorcadeService.save(joinedMotorcadeEntity);
        // 校验当前人数是否已满
        int count = joinedMotorcadeService.count(new LambdaQueryWrapper<JoinedMotorcadeEntity>()
                .eq(JoinedMotorcadeEntity::getMotorcadeId, joinedMotorcadeParam.getMotorcadeId()));
        MotorcadeEntity motorcadeEntity = motorcadeService.getById(joinedMotorcadeParam.getMotorcadeId());
        int total = motorcadeEntity.getAlreadyExisting() + count;
        Assert.isTrue(total<= motorcadeEntity.getMaximumNumber(),RS.FLEET_IS_FULL.message());

        // 加入聊天室
        GroupChatMemberEntity groupChatMemberEntity = new GroupChatMemberEntity();
        groupChatMemberEntity.setFirstMessageId(maxIdByGroupId);
        groupChatMemberEntity.setLastMessageId(maxIdByGroupId);
        groupChatMemberEntity.setUserId(user.getId());
        groupChatMemberEntity.setGroupId(one.getId());
        groupChatMemberService.save(groupChatMemberEntity);

        // 发送车队改变通知
        fleetChangesEventPublisher.publish(new FleetChangesEventMessage(
                total == motorcadeEntity.getMaximumNumber()?FleetChangesEnum.SUCCESS.getCode():FleetChangesEnum.JOIN.getCode()
                ,motorcadeEntity.getId()
                ,user.getId()));
        return R.success();
    }

    @ApiOperation(value = "离开车队", httpMethod = "POST")
    @PostMapping("/leave")
    public R leave(@RequestBody JoinedMotorcadeParam joinedMotorcadeParam) {
        UserVo user =(UserVo) UserLoginUtils.getUserInfo().getUser();

        joinedMotorcadeService.remove(new LambdaQueryWrapper<JoinedMotorcadeEntity>()
                .eq(JoinedMotorcadeEntity::getMotorcadeId,joinedMotorcadeParam.getMotorcadeId())
                .eq(JoinedMotorcadeEntity::getUserId,user.getId()));

        GroupChatEntity one = groupChatService.getOne(new LambdaQueryWrapper<GroupChatEntity>()
                .select(GroupChatEntity::getId)
                .eq(GroupChatEntity::getMotorcadeId, joinedMotorcadeParam.getMotorcadeId()));
        groupChatMemberService.remove(new LambdaQueryWrapper<GroupChatMemberEntity>()
                .eq(GroupChatMemberEntity::getGroupId,one.getId())
                .eq(GroupChatMemberEntity::getUserId,user.getId()));
        fleetChangesEventPublisher.publish(new FleetChangesEventMessage(
              FleetChangesEnum.LEAVE.getCode()
                ,joinedMotorcadeParam.getMotorcadeId()
                ,user.getId()));
        return R.success();
    }
}

