package com.bluetop.payment.core.pay;

import cn.hutool.json.JSONUtil;
import com.bluetop.payment.core.cons.IErrorCode;
import com.bluetop.payment.core.cons.PaymentException;
import com.bluetop.payment.core.cons.type.PayType;
import com.bluetop.payment.core.pay.request.PayQrCodeRequest;
import com.bluetop.payment.core.pay.response.PayQrCodeResonpse;
import com.bluetop.payment.core.storage.LocalConfigStorage;
import com.bluetop.payment.core.strategy.PayStrategy;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <微信二维码支付>
 *
 * @author zhouping
 * @version 1.0
 * @date 2020/9/23 2:53 下午
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Slf4j(topic = "payment-wxqrcode-service")
public class WeChartQrCodePay extends PayStrategy<PayQrCodeRequest, PayQrCodeResonpse> {

    @Autowired
    private WxPayService wxPayService;

    private LocalConfigStorage localConfigStorage;

    /**
     * 微信二维码支付
     * 1、二维码中的内容为链接，形式为：
     *    * weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXXX&time_stamp=XXXXXX&nonce_str=XXXXX
     *    * 其中XXXXX为商户需要填写的内容，商户将该链接生成二维码，如需要打印发布二维码，需要采用此格式。商户可调用第三方库生成二维码图片。
     * 2、扫码支付模式二生成二维码的方法
     *    * 对应链接格式：weixin：//wxpay/bizpayurl?sr=XXXXX。请商户调用第三方库将code_url生成二维码图片。
     *    * 该模式链接较短，生成的二维码打印到结账小票上的识别率较高。
     *
     * @param payQrCodeRequest
     * @return
     */
    @Override
    public PayQrCodeResonpse pay(PayQrCodeRequest payQrCodeRequest) {
        PayQrCodeResonpse qrCodeResonpse = new PayQrCodeResonpse();
        try {
            log.info("【{}】 invoke pay  ==========================>>>> request ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(payQrCodeRequest));
            if (StringUtils.isNotEmpty(payQrCodeRequest.getCodeUrl())) {
                byte[] bytes = wxPayService.createScanPayQrcodeMode2(payQrCodeRequest.getCodeUrl(), payQrCodeRequest.getLogoFile(), payQrCodeRequest.getSideLength());
                qrCodeResonpse.setBytes(bytes);
            } else if (StringUtils.isNotEmpty(payQrCodeRequest.getProductId()) && StringUtils.isEmpty(payQrCodeRequest.getCodeUrl())) {
                byte[] bytes = wxPayService.createScanPayQrcodeMode1(payQrCodeRequest.getProductId(), payQrCodeRequest.getLogoFile(), payQrCodeRequest.getSideLength());
                qrCodeResonpse.setBytes(bytes);
            } else {
                String codeUrl = wxPayService.createScanPayQrcodeMode1(payQrCodeRequest.getProductId());
                qrCodeResonpse.setCodeUrl(codeUrl);
            }
            log.info("【{}】 invoke pay  ==========================<<<< response ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(qrCodeResonpse));
            return qrCodeResonpse;
        } catch (Exception e) {
            log.error("【{}】 invoke scan qr code pay failed. ====================<<<< error : {}", getClass().getSimpleName(), ExceptionUtils.getRootCauseMessage(e));
            throw new PaymentException("微信扫码支付失败", IErrorCode.PAYMENT_ERROR);
        }
    }

    /**
     * 支付类型
     *
     * @return
     */
    @Override
    public PayType payType() {
        return PayType.WXPAY_QCODE;
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
