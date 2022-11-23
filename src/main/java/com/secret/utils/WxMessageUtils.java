package com.secret.utils;



import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RestController
public class WxMessageUtils {

    private String formId;
//    public R sendMessage() {
//        String userId = StpUtil.getLoginIdAsString();
//        // 测试
//        List<WxMaSubscribeMessage.MsgData> msgData = Arrays.asList(
//                new WxMaSubscribeMessage.MsgData("thing1", "嘿嘿"),
//                new WxMaSubscribeMessage.MsgData("date3", new DateTime(DateUtil.now(), DatePattern.NORM_DATETIME_FORMAT).toString())
//        );
//        try {
//            WxMaSubscribeMessage message = WxMaSubscribeMessage.builder()
//                    // 要给谁发送
//                    .toUser(userId)
//                    // 模板id
//                    .templateId("7oklMCAUD0zNAoTWikBOPSwVH2-XKC2-BJVqsUYGxgg")
//                    // 数据
//                    .data(msgData)
//                    .build();
//            wxMaService.getMsgService().sendSubscribeMsg(message);
//            return R.ok("发送成功");
//        } catch (WxErrorException e) {
//            log.error(e.toString());
//            return R.error(e.getError().getErrorMsg());
//        }
//    }
}
