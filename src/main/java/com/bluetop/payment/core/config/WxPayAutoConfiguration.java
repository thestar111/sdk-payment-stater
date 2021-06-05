package com.bluetop.payment.core.config;

import com.bluetop.payment.core.pay.*;
import com.bluetop.payment.core.storage.InMemoryConfigStorage;
import com.bluetop.payment.core.strategy.PayCommand;
import com.bluetop.payment.core.strategy.PayStrategy;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <微信支付配置>
 *
 * @author zhouping
 * @version 1.0
 * @date 2020/9/27 2:31 下午
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayAutoConfiguration {

    @Autowired
    private WxPayProperties wxPayProperties;

    /**
     * 微信支付
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = {"wxpay.enable"}, havingValue = "true", matchIfMissing = false)
    public WxPayService wxService() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(wxPayProperties.getAppId()));
        payConfig.setMchId(StringUtils.trimToNull(wxPayProperties.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(wxPayProperties.getMchKey()));
        payConfig.setSubAppId(StringUtils.trimToNull(wxPayProperties.getSubAppId()));
        payConfig.setSubMchId(StringUtils.trimToNull(wxPayProperties.getSubMchId()));
        payConfig.setKeyPath(StringUtils.trimToNull(wxPayProperties.getKeyPath()));

        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "wxpay.enable", havingValue = "true", matchIfMissing = true)
    public PayStrategy wechartWapPay() {
        PayStrategy wechartWapPay = new WeChartWapPay();
        InMemoryConfigStorage inMemoryConfigStorage = new InMemoryConfigStorage();
        inMemoryConfigStorage.setNotifyUrl(wxPayProperties.getNotify());
        inMemoryConfigStorage.setReturnUrl(wxPayProperties.getReturnUrl());
        wechartWapPay.setLocalConfigStorage(inMemoryConfigStorage);
        return wechartWapPay;
    }

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "wxpay.enable", havingValue = "true", matchIfMissing = true)
    public PayStrategy wechartPay() {
        PayStrategy wechartPay = new WeChartPay();
        InMemoryConfigStorage inMemoryConfigStorage = new InMemoryConfigStorage();
        inMemoryConfigStorage.setNotifyUrl(wxPayProperties.getNotify());
        inMemoryConfigStorage.setReturnUrl(wxPayProperties.getReturnUrl());
        wechartPay.setLocalConfigStorage(inMemoryConfigStorage);
        return wechartPay;
    }

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "wxpay.enable", havingValue = "true", matchIfMissing = true)
    public PayStrategy weChartQrCodePay() {
        PayStrategy weChartQrCodePay = new WeChartQrCodePay();
        InMemoryConfigStorage inMemoryConfigStorage = new InMemoryConfigStorage();
        inMemoryConfigStorage.setNotifyUrl(wxPayProperties.getNotify());
        inMemoryConfigStorage.setReturnUrl(wxPayProperties.getReturnUrl());
        weChartQrCodePay.setLocalConfigStorage(inMemoryConfigStorage);
        return weChartQrCodePay;
    }

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "wxpay.enable", havingValue = "true", matchIfMissing = true)
    public PayStrategy wechartMicroPay() {
        PayStrategy microPay = new WeChartMicroPay();
        InMemoryConfigStorage inMemoryConfigStorage = new InMemoryConfigStorage();
        inMemoryConfigStorage.setNotifyUrl(wxPayProperties.getNotify());
        inMemoryConfigStorage.setReturnUrl(wxPayProperties.getReturnUrl());
        microPay.setLocalConfigStorage(inMemoryConfigStorage);
        return microPay;
    }

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "wxpay.enable", havingValue = "true", matchIfMissing = true)
    public PayStrategy nativePayPay() {
        PayStrategy nativePay = new WeChartNativePay();
        InMemoryConfigStorage inMemoryConfigStorage = new InMemoryConfigStorage();
        inMemoryConfigStorage.setNotifyUrl(wxPayProperties.getNotify());
        inMemoryConfigStorage.setReturnUrl(wxPayProperties.getReturnUrl());
        nativePay.setLocalConfigStorage(inMemoryConfigStorage);
        return nativePay;
    }

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "wxpay.enable", havingValue = "true", matchIfMissing = true)
    public PayCommand wxpayCommand() {
        PayCommand payCommand = new WxPayCommand();
        return payCommand;
    }
}
