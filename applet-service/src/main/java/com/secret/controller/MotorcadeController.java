package com.secret.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.constant.RS;
import com.secret.entity.GroupChatEntity;
import com.secret.entity.MotorcadeEntity;
import com.secret.entity.UserEntity;
import com.secret.enums.applet.MotorcadeStatusEnum;
import com.secret.enums.applet.UserRoleEnum;
import com.secret.params.applet.MotorcadeCompleteParam;
import com.secret.params.applet.MotorcadeModifyParam;
import com.secret.params.applet.MotorcadeParam;
import com.secret.params.applet.MotorcadeQueryParam;
import com.secret.service.*;
import com.secret.utils.TransferUtils;
import com.secret.utils.UserLoginUtils;
import com.secret.vo.R;
import com.secret.vo.applet.MotorcadeVo;
import com.secret.vo.applet.UserVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 * 车队 前端控制器
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
@RestController
@RequestMapping("/motorcade")
public class MotorcadeController {

    @Autowired
    private MotorcadeService motorcadeService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private GroupChatMemberService groupChatMemberService;

    @Autowired
    private JoinedMotorcadeService joinedMotorcadeService;

    @ApiOperation(value = "发布车队信息", httpMethod = "POST")
    @PostMapping("/user/add")
    @Transactional
    public R<MotorcadeVo> updateInformation(@RequestBody MotorcadeParam motorcadeParam) {
        UserVo user = (UserVo)UserLoginUtils.getUserInfo().getUser();
        UserEntity userEntity = userService.getById(user.getId());
        Assert.isTrue(UserRoleEnum.RELEASE_GROUP.getCode().equals(userEntity.getRole()) , RS.NO_PUBLISHING_PERMISSION.message());
        MotorcadeEntity motorcadeEntity = new MotorcadeEntity();
        TransferUtils.transferBean(motorcadeParam,motorcadeEntity);
        motorcadeEntity.setUserId(user.getId());
        motorcadeEntity.setStatus(MotorcadeStatusEnum.HAVE_IN_HAND.getCode());
        motorcadeEntity.setClusteringNumber(motorcadeParam.getMaximumNumber());
        motorcadeService.save(motorcadeEntity);
        MotorcadeVo motorcadeVo=new MotorcadeVo();
        TransferUtils.transferBean(motorcadeEntity,motorcadeVo);
        // 加入车队
        joinedMotorcadeService.join(user.getId(),motorcadeEntity.getId());
        // 创建聊天室
        GroupChatEntity groupChat = groupChatService.createGroupChat(motorcadeEntity);
        // 加入群聊
        groupChatMemberService.joinGroupChat(user.getId(),groupChat.getId(),Boolean.TRUE);
        return R.success(motorcadeVo);
    }

    @ApiOperation(value = "删除车队信息")
    @PostMapping("/user/delete/{id}")
    public R<Integer> delete(@PathVariable Integer id) {
        motorcadeService.removeById(id);
        return R.success(id);
    }
    @ApiOperation(value = "更新车队信息", httpMethod = "POST")
    @PostMapping("/user/update")
    public R<MotorcadeVo> update(@RequestBody MotorcadeModifyParam motorcadeModifyParam) {
        MotorcadeEntity motorcadeEntity = new MotorcadeEntity();
        TransferUtils.transferBean(motorcadeModifyParam,motorcadeEntity);
        motorcadeService.updateById(motorcadeEntity);
        MotorcadeVo motorcadeVo = motorcadeService.getMotorcadeVoById(motorcadeEntity.getId());
        return R.success(motorcadeVo);
    }

    @ApiOperation(value = "拼车完成", httpMethod = "POST")
    @PostMapping("/user/complete")
    public R<String> complete(@RequestBody MotorcadeCompleteParam motorcadeCompleteParam) {
        motorcadeService.update(new LambdaUpdateWrapper<MotorcadeEntity>()
                .eq(MotorcadeEntity::getId,motorcadeCompleteParam.getId())
                .set(MotorcadeEntity::getStatus,MotorcadeStatusEnum.SUCCESS.getCode()));
        return R.success("");
    }

    @ApiOperation(value = "分页查询大厅车队信息", httpMethod = "POST")
    @PostMapping("/page")
    public R<Page<MotorcadeVo>> page(@RequestBody MotorcadeQueryParam motorcadeQueryParam) {
        Page<MotorcadeVo> motorcadeVoPage = motorcadeService.getMotorcadeVoPage(motorcadeQueryParam);
        return R.success(motorcadeVoPage);
    }

    @ApiOperation(value = "车队详情", httpMethod = "GET")
    @GetMapping("/detail/{id}")
    public R<MotorcadeVo> detail( @PathVariable Integer id) {
        MotorcadeVo motorcadeVo = motorcadeService.getMotorcadeVo(id);
        return R.success(motorcadeVo);
    }

}

