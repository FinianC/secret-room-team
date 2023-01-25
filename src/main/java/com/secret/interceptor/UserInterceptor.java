package com.secret.interceptor;

import com.secret.model.dto.User;
import com.secret.model.vo.UserVo;
import com.secret.utils.UserLoginUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 *用户拦截器
 **/
public class UserInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
                //这里就是token
                Object list = ((Map) raw).get("token");
                String token = ((ArrayList) list).get(0).toString();
                Object typeList = ((Map) raw).get("type");
                String type = ((ArrayList) typeList).get(0).toString();
                UserVo userVo = (UserVo) UserLoginUtils.getUserInfo(token).getUser();
                User user = new User();
                user.setUsername(token);
                user.setId(userVo.getId());
                user.setType(type);
                accessor.setUser(user);
        } else if (StompCommand.SEND.equals(accessor.getCommand())) {
            //发送数据

        }
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {

    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        return false;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        return null;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {

    }
}

