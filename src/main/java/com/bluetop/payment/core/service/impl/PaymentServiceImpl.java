/**
 * 文 件 名:  PaymentServiceImpl
 * 描    述:  <描述>
 * 修 改 人:  zhouping
 * 修改时间:  0:27
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bluetop.payment.core.service.impl;

import com.bluetop.payment.core.context.PaymentContext;
import com.bluetop.payment.core.service.PaymentService;
import com.bluetop.payment.core.strategy.PayStrategy;
import com.bluetop.payment.core.strategy.request.PayRequest;
import com.bluetop.payment.core.strategy.response.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <一句话功能简述>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/2 0:27
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Slf4j(topic = "payment-service")
@Component
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentContext paymentContext;

    /**
     * 第三方支付
     *
     * @param payRequest
     * @return
     */
    @Override
    public PayResponse pay(PayRequest payRequest) {
        PayStrategy pay = paymentContext.creator(payRequest.getPayType());
        log.info("【PayOrderController】invoke pay ===============================>>>> request : {}", payRequest);
        PayResponse payResponse = pay.pay(payRequest);
        log.info("【PayOrderController】invoke pay ===============================<<<< response : {}", payResponse);
        return payResponse;
    }
}
