//package com.secretroom.server;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.secretroom.utils.SpringUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.concurrent.CopyOnWriteArraySet;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@ServerEndpoint(value="/websocket")
//@Component
//@Slf4j
//public class WebSocketServer
//{
//    @Autowired
//    JmOrderService jmOrderService;
//    private static final AtomicInteger onlineCount = new AtomicInteger(0);
//
//    private static CopyOnWriteArraySet<Session> sessionSet = new CopyOnWriteArraySet<Session>();
//
//    @OnOpen
//    public void onOpen(Session session)
//    {
//        if(jmOrderService==null){
//            jmOrderService = SpringUtils.getBean(JmOrderService.class);
//        }
//        sessionSet.add(session);
//        onlineCount.incrementAndGet();
//        log.info("当前在线人数：{}",onlineCount);
//        Integer orderCount=jmOrderService.count(new LambdaQueryWrapper<JmOrderEntity>()
//                .eq(JmOrderEntity::getOrderStatus,"0"));
//        log.info("当前未处理订单:"+orderCount);
//        if(orderCount>0){
//            sendMessage(session, "当前未处理订单：" + orderCount);
//        }
//
//    }
//
//    @OnClose
//    public void onClose(Session session)
//    {
//        sessionSet.remove(session);
//        int count=onlineCount.decrementAndGet();
//        log.info("有一连接关闭！当前在线人数为:{}",count);
//    }
//
//    @OnError
//    public void onError(Session session, Throwable error)
//    {
//        log.error("消息发生错误：{},Session ID： {}",error.getMessage(),session.getId());
//    }
//
//
//    public void batchSendMesasge(String message) throws IOException
//    {
//        log.info("推送内容:{}",message);
//        for(Session session:sessionSet){
//            sendMessage(session, message);
//        }
//    }
//
//    public int getSize() {
//        return onlineCount.intValue();
//    }
//
//
//    public void sendMessage(Session session, String message)   {
//
//        if(session!=null)
//        {
//            synchronized (session) {
//                try {
//                    session.getBasicRemote().sendText(message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//}