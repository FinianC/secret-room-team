package com.secret.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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
}
