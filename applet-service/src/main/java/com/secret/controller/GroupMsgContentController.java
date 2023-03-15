package com.secret.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.constant.RS;
import com.secret.entity.GroupChatMemberEntity;
import com.secret.params.applet.GroupMsgContentQueryParam;
import com.secret.params.applet.UpdateReadingRecordsParam;
import com.secret.service.GroupChatMemberService;
import com.secret.service.GroupMsgContentService;
import com.secret.utils.UserLoginUtils;
import com.secret.vo.R;
import com.secret.vo.applet.GroupMsgContentVo;
import com.secret.vo.applet.UserVo;
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
        Page<GroupMsgContentVo> page = new Page<>(groupMsgContentQueryParam.getCurrent(), groupMsgContentQueryParam.getPageSize());
       return R.success(groupMsgContentService.pageByChatId(page,groupMsgContentQueryParam.getGroupChatId()));
    }

    @ApiOperation(value = "更新最新消息读取标记", httpMethod = "POST")
    @PostMapping("/updateReadingRecords")
    public  R updateReadingRecords( @RequestBody UpdateReadingRecordsParam updateReadingRecordsParam){
        UserVo user = (UserVo) UserLoginUtils.getUserInfo().getUser();
        Integer groupChatId = updateReadingRecordsParam.getGroupChatId();
        Integer maxIdByMotorcadeId = groupMsgContentService.getMaxIdByMotorcadeId(groupChatId);
        groupChatMemberService.update(new LambdaUpdateWrapper<GroupChatMemberEntity>()
                .set(GroupChatMemberEntity::getLastMessageId,maxIdByMotorcadeId)
                .eq(GroupChatMemberEntity::getGroupId,groupChatId)
                .eq(GroupChatMemberEntity::getUserId,user.getId())
                .ne(GroupChatMemberEntity::getLastMessageId,maxIdByMotorcadeId));
        return R.success();
    }

}

