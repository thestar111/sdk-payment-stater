package com.bluetop.payment.core.pay;

import cn.hutool.json.JSONUtil;
import com.bluetop.payment.core.cons.IErrorCode;
import com.bluetop.payment.core.cons.PaymentException;
import com.bluetop.payment.core.cons.type.PayType;
import com.bluetop.payment.core.pay.request.WxPayRequest;
import com.bluetop.payment.core.storage.LocalConfigStorage;
import com.bluetop.payment.core.strategy.PayStrategy;
import com.bluetop.payment.core.strategy.request.PayRequest;
import com.bluetop.payment.core.strategy.response.PayResponse;
import com.bluetop.payment.core.utils.StringUtil;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static com.bluetop.payment.core.cons.type.Device.WEB;
import static com.github.binarywang.wxpay.constant.WxPayConstants.TradeType.JSAPI;

/**
 * <微信公众号支付>
 *
 * @author zhouping
 * @version 1.0
 * @date 2020/9/23 2:53 下午
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Slf4j(topic = "payment-jsapi-service")
public class WeChartPay extends PayStrategy<PayRequest, PayResponse> {

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
    public PayResponse pay(PayRequest wechartPayRequest) {
        WxPayRequest wxPayRequest = new WxPayRequest();
        //4.发起微信统一支付
        wxPayRequest.setDeviceInfo(WEB.name());
        wxPayRequest.setSignType(WxPayConstants.SignType.MD5);
        wxPayRequest.setTradeType(JSAPI);
        wxPayRequest.setLimitPay(WxPayConstants.LimitPay.NO_CREDIT);
        wxPayRequest.setNotifyUrl(localConfigStorage.getNotifyUrl());
        wxPayRequest.setOutTradeNo(wechartPayRequest.getOutOrderNo());
        wxPayRequest.setSpbillCreateIp(wechartPayRequest.getIp());
        wxPayRequest.setBody(wechartPayRequest.getAttach());
        wxPayRequest.setAttach(wechartPayRequest.getAttach());
        wxPayRequest.setProductId(wechartPayRequest.getProductId());
        wxPayRequest.setTotalFee(wechartPayRequest.getAmount().intValue());
        WxPayMpOrderResult wxPayUnifiedOrderResult = null;
        try {
            log.info("【{}】 invoke pay  ==========================>>>> request ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(wxPayRequest));
            wxPayUnifiedOrderResult = wxPayService.createOrder(wxPayRequest);
            log.info("【{}】 invoke pay  ==========================<<<< response ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(wxPayUnifiedOrderResult));
            if (null != wxPayUnifiedOrderResult) {
                PayResponse payResponse = new PayResponse();
                payResponse.setAppId(wxPayUnifiedOrderResult.getAppId());
                payResponse.setPrepayId(wxPayUnifiedOrderResult.getPackageValue());
                payResponse.setNonceStr(wxPayUnifiedOrderResult.getNonceStr());
                payResponse.setSignType(wxPayUnifiedOrderResult.getSignType());
                payResponse.setTimeStamp(wxPayUnifiedOrderResult.getTimeStamp());
                payResponse.setSign(wxPayUnifiedOrderResult.getPaySign());
                if (StringUtil.isNotBlank(wxPayUnifiedOrderResult.getPackageValue())){
                    payResponse.setPrepayId(wxPayUnifiedOrderResult.getPackageValue().split("=")[1]);
                }
                return payResponse;
            } else {
                // 微信支付失败
                throw new PaymentException("微信公众号统一支付失败", IErrorCode.WX_PAY_ERROR);
            }
        } catch (WxPayException e) {
            log.error("【WeChartPay】 invoke jsapi pay failed. ====================<<<< error : {}", ExceptionUtils.getRootCauseMessage(e));
            throw new PaymentException("微信公众号统一支付失败", IErrorCode.PAYMENT_ERROR);
        }
    }

    /**
     * 支付类型
     *
     * @return
     */
    @Override
    public PayType payType() {
        return PayType.JSAPI;
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
