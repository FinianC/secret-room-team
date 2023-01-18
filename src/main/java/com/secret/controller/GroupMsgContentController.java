package com.secret.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.constant.RS;
import com.secret.model.params.GroupMsgContentQueryParam;
import com.secret.model.vo.GroupMsgContentVo;
import com.secret.model.vo.R;
import com.secret.model.vo.UserVo;
import com.secret.service.GroupChatMemberService;
import com.secret.service.GroupMsgContentService;
import com.secret.utils.UserLoginUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 群聊消息 前端控制器
 * </p>
 *
 * @author chenDi
 * @since 2022-12-04
 */
@RestController
@RequestMapping("/user/groupMsgContent")
public class GroupMsgContentController {

    @Autowired
    private GroupMsgContentService groupMsgContentService;

    @Autowired
    private GroupChatMemberService groupChatMemberService;


    @ApiOperation(value = "分页查询消息分页列表", httpMethod = "POST")
    @PostMapping("/page")
    public  R<Page<GroupMsgContentVo>> pageByChatId(@RequestBody GroupMsgContentQueryParam groupMsgContentQueryParam){
        UserVo user = (UserVo) UserLoginUtils.getUserInfo().getUser();
        Boolean groupMember = groupChatMemberService.isGroupMember(user.getId(), groupMsgContentQueryParam.getGroupChatId());
        Assert.isTrue(groupMember, RS.GROUP_CHAT_NOT_EXIST.message());
        Page page = new Page<>(groupMsgContentQueryParam.getCurrent(), groupMsgContentQueryParam.getPageSize());
       return R.success(groupMsgContentService.pageByChatId(page,groupMsgContentQueryParam.getGroupChatId()));
    }

}

