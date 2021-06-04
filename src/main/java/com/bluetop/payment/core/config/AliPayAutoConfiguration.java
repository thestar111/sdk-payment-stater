package com.bluetop.payment.core.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.bluetop.payment.core.pay.AliQrCodePay;
import com.bluetop.payment.core.pay.AliWapPay;
import com.bluetop.payment.core.pay.AliWebPay;
import com.bluetop.payment.core.storage.InMemoryConfigStorage;
import com.bluetop.payment.core.strategy.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <支付宝配置>
 *
 * @author zhouping
 * @version 1.0
 * @date 2020/9/24 4:36 下午
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Configuration
@ConditionalOnClass(AlipayClient.class)
@EnableConfigurationProperties(AliPayProperties.class)
public class AliPayAutoConfiguration {

    @Autowired
    private AliPayProperties aliPayProperties;

    /**
     * Ali客户端
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "alipay.enable", havingValue = "true", matchIfMissing = false)
    public AlipayClient alipayClient() {
        // 实例化支付宝支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayProperties.getGatewayUrl(),
                aliPayProperties.getAppId(),
                aliPayProperties.getPrivateKey(),
                "json",
                aliPayProperties.getCharset(),
                aliPayProperties.getAlipayPublicKey(),
                aliPayProperties.getSignType());
        return alipayClient;
    }

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "alipay.enable", havingValue = "true", matchIfMissing = true)
    public PayStrategy aliWapPay() {
        PayStrategy aliWapPay = new AliWapPay();
        InMemoryConfigStorage inMemoryConfigStorage = new InMemoryConfigStorage();
        inMemoryConfigStorage.setNotifyUrl(aliPayProperties.getNotifyUrl());
        inMemoryConfigStorage.setReturnUrl(aliPayProperties.getReturnUrl());
        aliWapPay.setLocalConfigStorage(inMemoryConfigStorage);
        return aliWapPay;
    }

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "alipay.enable", havingValue = "true", matchIfMissing = true)
    public PayStrategy aliWebPay() {
        PayStrategy aliWebPay = new AliWebPay();
        InMemoryConfigStorage inMemoryConfigStorage = new InMemoryConfigStorage();
        inMemoryConfigStorage.setNotifyUrl(aliPayProperties.getNotifyUrl());
        inMemoryConfigStorage.setReturnUrl(aliPayProperties.getReturnUrl());
        aliWebPay.setLocalConfigStorage(inMemoryConfigStorage);
        return aliWebPay;
    }

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "alipay.enable", havingValue = "true", matchIfMissing = true)
    public PayStrategy aliQrCodePay() {
        PayStrategy aliQrCodePay = new AliQrCodePay();
        InMemoryConfigStorage inMemoryConfigStorage = new InMemoryConfigStorage();
        inMemoryConfigStorage.setNotifyUrl(aliPayProperties.getNotifyUrl());
        inMemoryConfigStorage.setReturnUrl(aliPayProperties.getReturnUrl());
        aliQrCodePay.setLocalConfigStorage(inMemoryConfigStorage);
        return aliQrCodePay;
    }
}
