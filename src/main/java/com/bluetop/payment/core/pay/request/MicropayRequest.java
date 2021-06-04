/**
 * 文 件 名:  MicropayRequest
 * 描    述:  <描述>
 * 修 改 人:  zhouping
 * 修改时间:  15:52
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bluetop.payment.core.pay.request;

import com.bluetop.payment.core.strategy.request.PayRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * <POST刷卡支付>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/5/30 15:52
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MicropayRequest extends PayRequest {

    /** 扫码支付付款码，设备读取用户微信中的条码或者二维码信息 */
    private String authCode;

    /**
     * 获取限制支付方式
     *
     * @return
     */
    public String getLimitPay() {
        if (Objects.isNull(getEnableCredit()) || Boolean.FALSE == getEnableCredit()) {
            return WxPayConstants.LimitPay.NO_CREDIT;
        }
        return null;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MicropayRequest.class.getSimpleName() + "[", "]")
                .add("authCode='" + authCode + "'")
                .add("super='" + super.toString() + "'")
                .toString();
    }
}