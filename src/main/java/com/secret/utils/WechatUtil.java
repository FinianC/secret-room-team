package com.secret.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.secret.model.dto.wxsmallTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        JSONObject jsonObject = JSONObject.parseObject(jsonString);

        return jsonObject;
    }

//    public Object sendDYTemplateMessage(String openId) throws Exception {
//
//        System.err.println("openId:"+openId);
//        wxsmallTemplate tem = new wxsmallTemplate();
//        //跳转小程序页面路径
//        tem.setPage("pages/index/index");
//        //模板消息id
//        tem.setTemplateId("-UBAuupYlK2RAbxYvhk6UvK48ujQD72RpEOdkF-sJ2s");
//        //给谁推送 用户的openid （可以调用根据code换openid接口)
//        tem.setToUser(openId);
//        //==========================================创建一个参数集合========================================================
//
////        List<wxsmallTemplateParam> paras = new ArrayList<wxsmallTemplateParam>();
////        //这个是满参构造 keyword1代表的第一个提示  红包已到账这是提示 #DC143C这是色值不过废弃了
////        wxsmallTemplateParam templateParam = new wxsmallTemplateParam(
////                "thing2", "红包已到账", "#DC143C");
////
////        paras.add(templateParam);
////        paras.add(new wxsmallTemplateParam("phrase3", "刘骞", ""));
////        tem.setData(paras);
//        //模板需要放大的关键词，不填则默认无放大
////        tem.setToken(getAccessToken());
//        //=========================================封装参数集合完毕========================================================
//        try {
//            //进行推送
//            //获取微信小程序配置：
//            if(sendTemplateMsg1(getAccess_token(APPID1,AppSecret1), tem)){
//                return "推送成功";
//            }else{
//                JSONObject jsonObject = new JSONObject();   //返回JSON格式数据
//                jsonObject.put("buTie",tem);
//                return jsonObject;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "推送失败";
//    }

//    public static boolean sendTemplateMsg1(String token,wxsmallTemplate template) {
//        System.err.println("token:"+token);
//        boolean flag = false;
//
//        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN";
////        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
//
//        requestUrl = requestUrl.replace("ACCESS_TOKEN", token);
//        JSONObject jsonResult = JSON.parseObject(post(JSON.parseObject(template.toJSON()) ,requestUrl)) ;
//        if (jsonResult != null) {
//            Integer errorCode = jsonResult.getInteger("errcode");
//            String errorMessage = jsonResult.getString("errmsg");
//            if (errorCode == 0) {
//                flag = true;
//            } else {
//                System.out.println("模板消息发送失败:" + errorCode + "," + errorMessage);
//                flag = false;
//            }
//        }
//        return flag;
//    }

    public String getAccess_token(String appid, String appsecret) {
        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret;
//                      https://api.weixin.qq.com/cgi-bin/token.
        JSONObject jsonObject = doGet1(url);
        System.out.println(jsonObject.toString());
        String errCode = jsonObject.getString("expires_in");
        if (!StringUtils.isEmpty(errCode)  && !StringUtils.isEmpty(jsonObject.getString("access_token").toString())) {
            String token = jsonObject.get("access_token").toString();
            return token;
        } else {
            return null;
        }
    }

    public static JSONObject doGet1(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            HttpResponse httpResponse = client.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                jsonObject = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //        释放连接
                httpGet.releaseConnection();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
    //post请求
    public static String post(JSONObject json,String URL) {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";

        try {

            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            System.out.println(result);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求服务器成功，做相应处理");
            } else {
                System.out.println("请求服务端失败");
            }
        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }

        return result;
    }
}
