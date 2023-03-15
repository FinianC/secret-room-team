package com.secret.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.secret.constant.RS;
import com.secret.dto.applet.FleetChangesEventMessage;
import com.secret.entity.GroupChatEntity;
import com.secret.entity.JoinedMotorcadeEntity;
import com.secret.entity.MotorcadeEntity;
import com.secret.enums.applet.FleetChangesEnum;
import com.secret.event.producer.FleetChangesEventPublisher;
import com.secret.params.applet.JoinedMotorcadeParam;
import com.secret.params.applet.KickOutParam;
import com.secret.service.GroupChatMemberService;
import com.secret.service.GroupChatService;
import com.secret.service.JoinedMotorcadeService;
import com.secret.service.MotorcadeService;
import com.secret.utils.UserLoginUtils;
import com.secret.vo.R;
import com.secret.vo.applet.MotorcadeVo;
import com.secret.vo.applet.UserVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private GroupChatMemberService groupChatMemberService;

    @Autowired
    private GroupChatService groupChatService;



    @ApiOperation(value = "加入车队", httpMethod = "POST")
    @PostMapping("/join")
    @Transactional
    public R<MotorcadeVo> join(@RequestBody JoinedMotorcadeParam joinedMotorcadeParam) {
        UserVo user =(UserVo) UserLoginUtils.getUserInfo().getUser();
        // check
        MotorcadeEntity motorcadeEntity = motorcadeService.getById(joinedMotorcadeParam.getMotorcadeId());
        Assert.notNull(motorcadeEntity, RS.FLEET_DOES_NOT_EXIST.message());
        JoinedMotorcadeEntity joinedMotorcadeEntity = joinedMotorcadeService.getOne(new LambdaQueryWrapper<JoinedMotorcadeEntity>()
                .eq(JoinedMotorcadeEntity::getMotorcadeId, joinedMotorcadeParam.getMotorcadeId())
                .eq(JoinedMotorcadeEntity::getUserId, user.getId()));
        Assert.isNull(joinedMotorcadeEntity, RS.DO_NOT_JOIN_AGAIN.message());

        GroupChatEntity one = groupChatService.getOne(new LambdaQueryWrapper<GroupChatEntity>()
                .select(GroupChatEntity::getId)
                .eq(GroupChatEntity::getMotorcadeId, joinedMotorcadeParam.getMotorcadeId()));

        // 校验当前人数是否已满
        int count = joinedMotorcadeService.count(new LambdaQueryWrapper<JoinedMotorcadeEntity>()
                .eq(JoinedMotorcadeEntity::getMotorcadeId, joinedMotorcadeParam.getMotorcadeId()));
        int total = motorcadeEntity.getAlreadyExisting()+count;
        Assert.isTrue(total <  motorcadeEntity.getMaximumNumber(),RS.FLEET_IS_FULL.message());
        // 加入车队
        joinedMotorcadeService.join(user.getId(),joinedMotorcadeParam.getMotorcadeId());

        // 加入聊天室
        groupChatMemberService.joinGroupChat(user.getId(),one.getId());

        // 发送车队改变通知
        fleetChangesEventPublisher.publish(new FleetChangesEventMessage(
                total+1 == motorcadeEntity.getMaximumNumber()? FleetChangesEnum.SUCCESS.getCode():FleetChangesEnum.JOIN.getCode()
                ,motorcadeEntity.getId()
                ,user.getId()));
        MotorcadeVo motorcadeVo = motorcadeService.getMotorcadeVo(joinedMotorcadeParam.getMotorcadeId());
        return R.success(motorcadeVo);
    }

    @ApiOperation(value = "离开车队", httpMethod = "POST")
    @PostMapping("/leave")
    @Transactional
    public R<MotorcadeVo> leave(@RequestBody JoinedMotorcadeParam joinedMotorcadeParam) {
        UserVo user =(UserVo) UserLoginUtils.getUserInfo().getUser();
        joinedMotorcadeService.leave(user.getId(),joinedMotorcadeParam.getMotorcadeId());
        MotorcadeVo motorcadeVo = motorcadeService.getMotorcadeVo(joinedMotorcadeParam.getMotorcadeId());
        return R.success(motorcadeVo);
    }

    @ApiOperation(value = "踢出车队", httpMethod = "POST")
    @PostMapping("/kickOut")
    @Transactional
    public R<MotorcadeVo> kickOut(@RequestBody KickOutParam kickOutParam) {
        UserVo user =(UserVo) UserLoginUtils.getUserInfo().getUser();
        MotorcadeEntity motorcadeEntity = motorcadeService.getById(kickOutParam.getMotorcadeId());
        Assert.notNull(motorcadeEntity,RS.FLEET_DOES_NOT_EXIST.message());
        Assert.isTrue(motorcadeEntity.getUserId().equals(user.getId()),RS.INSUFFICIENT_PERMISSIONS.message());
        joinedMotorcadeService.leave(kickOutParam.getUserId(),kickOutParam.getMotorcadeId());
        MotorcadeVo motorcadeVo = motorcadeService.getMotorcadeVo(kickOutParam.getMotorcadeId());
        return R.success(motorcadeVo);
    }
}

