package com.secret.config;


import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MiniAppBean {

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;

    /** 消息模板ID.*/
    @Value("${wechat.successTemplateId}")
    private String successTemplateId;
    /** 消息模板ID.*/
    @Value("${wechat.cancelTemplateId}")
    private String cancelTemplateId;

    /** 消息模板ID.*/
    @Value("${wechat.failTemplateId}")
    private String failTemplateId;

    @Value("${wechat.pay.mchId}")
    private String mchId;
    /** mchKey ： 微信支付商户密钥 */
    @Value("${wechat.pay.mchKey}")
    private String mchKey;
    /** mchKey ： 商户证书目录 */
    @Value("${wechat.pay.keyPath}")
    private String keyPath;

    @Value("${wechat.pay.payNotify}")
    private String payNotify;

    @Value("${wechat.pay.refund}")
    private String refund;

    @Value("${wechat.pay.timeout}")
    private Long payTimeOut;

    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxPayService() {

        //实例payConfig 设置固定参数
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(appId));
        payConfig.setMchId(StringUtils.trimToNull(mchId));
        payConfig.setMchKey(StringUtils.trimToNull(mchKey));
        payConfig.setKeyPath(StringUtils.trimToNull(keyPath));
        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

}
