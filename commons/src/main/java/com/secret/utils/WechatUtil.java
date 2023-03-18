package com.secret.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
@Slf4j
public  class WechatUtil {
    public static JSONObject getOpenid(String appId,String appSecret,String code){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code=" + code + "&grant_type=authorization_code";
        HttpConnection httpConnection = new HttpConnection();
        String jsonString = null;
        try {
            jsonString = httpConnection.get(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.parseObject(jsonString);
    }

    public  static JSONObject getPhoneNumber(String appId,String appSecret,String code) {
        JSONObject phone;
        // 获取token
        String token_url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId, appSecret);
        HttpConnection httpConnection = new HttpConnection();
        try {
            JSONObject token = JSON.parseObject(httpConnection.get(token_url));
            if (token == null) {
                log.info("获取token失败");
                return null;
            }
            String accessToken = token.getString("access_token");
            if (StringUtils.isEmpty(accessToken)) {
                log.info("获取token失败");
                return null;
            }
            log.info("token : {}", accessToken);
            //获取phone
            String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber"
                    + "?access_token=" + accessToken;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", code);
            String reqJsonStr = JSONObject.toJSONString(jsonObject);
            phone = JSON.parseObject(httpConnection.post(url, reqJsonStr));

            if (phone == null) {
                log.info("获取手机号失败");
                return null;
            }
            return phone;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
