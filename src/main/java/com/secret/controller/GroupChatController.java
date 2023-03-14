package com.secret.controller;


import com.secret.model.vo.ChatListVo;
import com.secret.model.vo.R;
import com.secret.model.vo.UserVo;
import com.secret.service.GroupChatService;
import com.secret.utils.UserLoginUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenDi
 * @since 2022-12-08
 */
@RestController
@RequestMapping("/groupChat")
public class GroupChatController {


    @Autowired
    private GroupChatService groupChatService;


    @ApiOperation("聊天框列表")
    @GetMapping("/list")
    public R<List<ChatListVo>> list(){
        UserVo user =UserLoginUtils.<UserVo>getUserInfo().getUser();
        List<ChatListVo> chatByUserId = groupChatService.getChatByUserId(user.getId());
        return R.success(chatByUserId);
    }
}

