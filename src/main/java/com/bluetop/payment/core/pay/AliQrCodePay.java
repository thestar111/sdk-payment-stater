package com.bluetop.payment.core.pay;

import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.bluetop.payment.core.cons.IErrorCode;
import com.bluetop.payment.core.cons.PaymentException;
import com.bluetop.payment.core.cons.type.PayType;
import com.bluetop.payment.core.pay.response.AliPayResponse;
import com.bluetop.payment.core.storage.LocalConfigStorage;
import com.bluetop.payment.core.strategy.PayStrategy;
import com.bluetop.payment.core.strategy.request.PayRequest;
import com.bluetop.payment.core.utils.GUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * <阿里WAP支付>
 *
 * @author zhouping
 * @version 1.0
 * @date 2020/9/23 2:55 下午
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Slf4j(topic = "payment-aliqrcode-service")
public class AliQrCodePay extends PayStrategy<PayRequest, AliPayResponse> {

    @Autowired
    private AlipayClient alipayClient;

    private LocalConfigStorage localConfigStorage;

    /**
     * 阿里支付
     *
     * @param   payRequest
     * @return  返回Form表单数据
     */
    @Override
    public AliPayResponse pay(PayRequest payRequest) {
        AlipayTradePrecreateRequest alipayTradePrecreateRequest = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setBody(payRequest.getBody());
        model.setSubject(payRequest.getProductName());
        // 商户订单号 就是商户后台生成的订单号
        model.setOutTradeNo(payRequest.getOutOrderNo());
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天 (屁股后面的字母一定要带，不然报错)
        model.setTimeoutExpress(payRequest.getExpireTime());
        // 订单总金额 ，默认单位为元，精确到小数点后两位，取值范围[0.01,100000000]
        model.setTotalAmount(payRequest.getAmount().divide(new BigDecimal(100)).toString());
        // 商户门店编号
        model.setStoreId(GUID.get());
        alipayTradePrecreateRequest.setBizModel(model);
        alipayTradePrecreateRequest.setNotifyUrl(localConfigStorage.getNotifyUrl());

        // 通过api的方法请求阿里接口获得反馈
        AlipayTradePrecreateResponse response = null;
        try {
            log.info("【{}】 invoke pay  ==========================>>>> request ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(alipayTradePrecreateRequest));
            response = alipayClient.pageExecute(alipayTradePrecreateRequest);
            log.info("【{}】 invoke pay  ==========================<<<< response ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(response));
            if (response.isSuccess()) {
                AliPayResponse payResponse = new AliPayResponse();
                payResponse.setQrcode(response.getQrCode());
                payResponse.setOutOrderNo(response.getOutTradeNo());
                return payResponse;
            } else {
                throw new PaymentException("支付宝二维码支付失败", IErrorCode.PAYMENT_ERROR);
            }
        } catch (AlipayApiException e) {
            log.error("【AliQrCodePay】 invoke pay failed. ====================<<<< error : {}", ExceptionUtils.getRootCauseMessage(e));
            throw new PaymentException("支付宝二维码支付失败", IErrorCode.PAYMENT_ERROR);
        }
    }

    /**
     * 支付类型
     *
     * @return
     */
    @Override
    public PayType payType() {
        return PayType.ALIPAY_QCODE;
    }

    /**
     * 本地缓存信息
     *
     * @param localConfigStorage
     */
    @Override
    public void setLocalConfigStorage(LocalConfigStorage localConfigStorage) {
        this.localConfigStorage = localConfigStorage;
    }
}
