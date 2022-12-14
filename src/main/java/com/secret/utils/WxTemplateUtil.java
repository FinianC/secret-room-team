package com.secret.utils;



import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.secret.model.dto.WxTemplateDTO;
import com.secret.model.vo.WxAccessTokenVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;


@Slf4j
@RestController
public class WxTemplateUtil {

    /**
     * 获取access_token的接口地址（GET） 限200（次/天）
     */
    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 发送模板消息（POST）
     */
    public final static String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";

    /**
     * 接口描述：请求工具
     * @date 2018/10/8 15:30
     * @params [requestUrl, requestMethod, outputStr]
     * @return net.sf.json.JSONObject
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if(RequestMethod.GET.equals(requestMethod)){
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if(null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            System.out.println("Weixin server connection timed out.");
        } catch (Exception e) {
            System.out.println("https request error:{}" + e);
        }
        return jsonObject;
    }

    /**
     * 接口描述：获取access_token
     * @date 2018/10/8 15:35
     * @params [appId, appSecret]
     * @return com.gzschool.prismtestcms.model.vo.WxAccessTokenVO
     */
    public static WxAccessTokenVO getAccessToken(String appId, String appSecret) {

        WxAccessTokenVO wxAccessTokenVO = null;

        String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appId).replace("APPSECRET", appSecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                wxAccessTokenVO = new WxAccessTokenVO();
                wxAccessTokenVO.setAccessToken(jsonObject.getString("access_token"));
                wxAccessTokenVO.setExpiresIn(jsonObject.getInteger("expires_in"));
            } catch (JSONException e) {
                wxAccessTokenVO = null;
                // 获取token失败
               log.error("获取token失败",e);
            }
        }
        return wxAccessTokenVO;
    }

    /**
     * 接口描述：消息模板发送
     * @date 2018/10/8 15:44
     * @params [wxTemplateDTO, accessToken]
     * @return net.sf.json.JSONObject
     */
    public static JSONObject sendTemplateMsg(WxTemplateDTO wxTemplateDTO, String accessToken) {

        String url = SEND_TEMPLATE_URL.replace("ACCESS_TOKEN", accessToken);
        // 将模板数据对象转换成json字符串
        String wxTemplate = JSONObject.toJSONString(wxTemplateDTO);
        //发送请求
        JSONObject result = httpRequest(url, "POST", wxTemplate);

        if (result != null) {
            return result;
        } else{
            return null;
        }
    }
}
