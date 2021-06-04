package com.bluetop.payment.core.pay;

import cn.hutool.json.JSONUtil;
import com.bluetop.payment.core.cons.IErrorCode;
import com.bluetop.payment.core.cons.PaymentException;
import com.bluetop.payment.core.cons.type.PayType;
import com.bluetop.payment.core.pay.request.WxPayRequest;
import com.bluetop.payment.core.pay.response.PayQrCodeResonpse;
import com.bluetop.payment.core.storage.LocalConfigStorage;
import com.bluetop.payment.core.strategy.PayStrategy;
import com.bluetop.payment.core.strategy.request.PayRequest;
import com.bluetop.payment.core.strategy.response.PayResponse;
import com.bluetop.payment.core.utils.StringUtil;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static com.bluetop.payment.core.cons.type.Device.WEB;
import static com.github.binarywang.wxpay.constant.WxPayConstants.TradeType.JSAPI;
import static com.github.binarywang.wxpay.constant.WxPayConstants.TradeType.NATIVE;

/**
 * <微信公众号支付>
 *
 * @author zhouping
 * @version 1.0
 * @date 2020/9/23 2:53 下午
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Slf4j(topic = "payment-native-service")
public class WeChartNativePay extends PayStrategy<PayRequest, PayQrCodeResonpse> {

    @Autowired
    private WxPayService wxPayService;

    private LocalConfigStorage localConfigStorage;

    /**
     * 微信支付
     *
     * @param wechartPayRequest
     * @return
     */
    @Override
    public PayQrCodeResonpse pay(PayRequest wechartPayRequest) {
        WxPayUnifiedOrderRequest wxPayRequest = new WxPayUnifiedOrderRequest();
        //4.发起微信统一支付
        wxPayRequest.setTradeType(NATIVE);
        wxPayRequest.setDeviceInfo(WEB.name());
        wxPayRequest.setSignType(WxPayConstants.SignType.MD5);
        wxPayRequest.setNotifyUrl(localConfigStorage.getNotifyUrl());
        wxPayRequest.setOutTradeNo(wechartPayRequest.getOutOrderNo());
        wxPayRequest.setSpbillCreateIp(wechartPayRequest.getIp());
        wxPayRequest.setBody(wechartPayRequest.getAttach());
        wxPayRequest.setAttach(wechartPayRequest.getAttach());
        wxPayRequest.setProductId(wechartPayRequest.getProductId());
        wxPayRequest.setTotalFee(wechartPayRequest.getAmount().intValue());
        WxPayUnifiedOrderResult wxPayUnifiedOrderResult = null;
        try {
            log.info("【{}】 invoke pay  ==========================>>>> request ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(wxPayRequest));
            wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayRequest);
            log.info("【{}】 invoke pay  ==========================<<<< response ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(wxPayUnifiedOrderResult));
            if (!Objects.isNull(wxPayUnifiedOrderResult)) {
                PayQrCodeResonpse payResponse = new PayQrCodeResonpse();
                payResponse.setNonceStr(wxPayUnifiedOrderResult.getNonceStr());
                payResponse.setPrepayId(wxPayUnifiedOrderResult.getPrepayId());
                payResponse.setCodeUrl(wxPayUnifiedOrderResult.getCodeURL());
                return payResponse;
            } else {
                // 微信支付失败
                throw new PaymentException("微信预支付统一支付失败", IErrorCode.WX_PAY_ERROR);
            }
        } catch (WxPayException e) {
            log.error("【WeChartPay】 invoke native pay failed. ====================<<<< error : {}", ExceptionUtils.getRootCauseMessage(e));
            throw new PaymentException("微信预支付统一支付失败", IErrorCode.PAYMENT_ERROR);
        }
    }

    /**
     * 支付类型
     *
     * @return
     */
    @Override
    public PayType payType() {
        return PayType.NATIVE;
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
