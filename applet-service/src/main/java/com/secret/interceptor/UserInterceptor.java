package com.secret.interceptor;

import com.secret.utils.WebSocketUser;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

/**
 *用户拦截器
 **/
public class UserInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

        } else if (StompCommand.SEND.equals(accessor.getCommand())) {
//            User user = (User)accessor.getUser();
//            if(user==null){
//                Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
//                //这里就是token
//                Object list = ((Map) raw).get("token");
//                String token = ((ArrayList) list).get(0).toString();
//                Object typeList = ((Map) raw).get("type");
//                String type = ((ArrayList) typeList).get(0).toString();
//                UserVo userVo = (UserVo) UserLoginUtils.getUserInfo(token).getUser();
//                user = new User();
//                user.setUsername(token);
//                user.setId(userVo.getId());
//                user.setType(type);
//                accessor.setUser(user);
//            }
            //发送数据

        }else if(StompCommand.DISCONNECT.equals(accessor.getCommand())){
           WebSocketUser.getConcurrentHashMap().remove(accessor.getSessionId());
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

