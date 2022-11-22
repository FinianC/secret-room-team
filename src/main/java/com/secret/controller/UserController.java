package com.secret.controller;


import ch.qos.logback.core.util.TimeUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.secret.constant.RS;
import com.secret.model.entity.UserEntity;
import com.secret.model.params.UserGetOpenIdParam;
import com.secret.model.params.UserModifyParam;
import com.secret.model.vo.R;
import com.secret.model.vo.UserVerificationVo;
import com.secret.model.vo.UserVo;
import com.secret.service.UserService;
import com.secret.utils.RedisUtils;
import com.secret.utils.TransferUtils;
import com.secret.utils.UserLoginUtils;
import com.secret.utils.WechatUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;


    @ApiOperation(value = "微信code登录", notes = "用户登录信息在header里面获取 token", httpMethod = "POST")
    @PostMapping("/getOpenId")
    public R<UserVerificationVo<UserVo>> getOpenId(@RequestBody UserGetOpenIdParam userGetOpenIdParam) {
        JSONObject jsonObject = WechatUtil.getOpenid(appId, appSecret, userGetOpenIdParam.getCode());
        String openId = jsonObject.getString("openid");
        if (StringUtils.isEmpty(openId)) {
            log.error("getOpenid error {}", JSONObject.toJSONString(jsonObject));
            return R.fail(RS.MENU_NOT_FOUNT);
        }
        UserEntity user = userService.getOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getOpenId, openId));
        if (user == null) {
            user = new UserEntity();
            BeanUtils.copyProperties(userGetOpenIdParam, user);
            user.setOpenId(openId);
            userService.save(user);
        }
        String token = RedisUtils.get(openId, String.class);
        if (StringUtils.isEmpty(token)) {
            token = RedisUtils.createToken();
            UserVerificationVo<UserVo> userVerificationVo = new UserVerificationVo<UserVo>();
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            userVerificationVo.setUser(userVo);
            userVerificationVo.setToken(token);
            userVerificationVo.setIsAuthentication(true);
            RedisUtils.set(token, userVerificationVo, 86400);
            RedisUtils.set(openId, token, 86400);
        }
        return R.success(RedisUtils.get(token, UserVerificationVo.class));
    }

    @ApiOperation(value = "更新用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public R<UserVerificationVo<UserVo>> updateInformation(@RequestBody UserModifyParam userModifyParam) {
        UserVerificationVo<UserVo> userInfo = UserLoginUtils.getUserInfo();
        UserVo user = userInfo.getUser();
        UserEntity userEntity = new UserEntity();
        TransferUtils.transferBean(userModifyParam,userEntity);
        userService.updateById(userEntity);
        UserEntity byId = userService.getById(userEntity.getId());
        TransferUtils.transferBean(byId,user);
        userInfo.setUser(user);
        return R.success(userInfo);
    }

}

