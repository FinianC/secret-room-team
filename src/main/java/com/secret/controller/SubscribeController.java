package com.secret.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.secret.model.entity.SubscribeEntity;
import com.secret.model.entity.UserEntity;
import com.secret.model.params.SubscribeParam;
import com.secret.model.vo.R;
import com.secret.model.vo.UserVerificationVo;
import com.secret.model.vo.UserVo;
import com.secret.service.SubscribeService;
import com.secret.service.UserService;
import com.secret.utils.TransferUtils;
import com.secret.utils.UserLoginUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订阅消息 前端控制器
 * </p>
 *
 * @author chenDi
 * @since 2022-11-24
 */
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private UserService userService;
    @Autowired
    private SubscribeService subscribeService;

    @ApiOperation(value = "消息订阅", httpMethod = "POST")
    @PostMapping("/user/add")
    public R add(@RequestBody SubscribeParam subscribeParam) {
        UserVo user = (UserVo)UserLoginUtils.getUserInfo().getUser();
        UserEntity byId = userService.getOne(new LambdaQueryWrapper<UserEntity>()
                .select(UserEntity::getId,UserEntity::getOpenId)
                .eq(UserEntity::getId,user.getId()));
        SubscribeEntity subscribeEntity = new SubscribeEntity();
        TransferUtils.transferBean(subscribeParam,subscribeEntity);
        subscribeEntity.setOpenid(byId.getOpenId());
        subscribeService.save(subscribeEntity);
        return R.success();
    }

}

