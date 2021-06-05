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

import com.bluetop.payment.core.command.*;
import com.bluetop.payment.core.cons.R;
import com.bluetop.payment.core.context.PaymentContext;
import com.bluetop.payment.core.service.PaymentService;
import com.bluetop.payment.core.strategy.PayCommand;
import com.bluetop.payment.core.strategy.PayStrategy;
import com.bluetop.payment.core.strategy.request.PayRequest;
import com.bluetop.payment.core.strategy.response.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <第三方支付接口服务>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/2 0:27
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Component
@Slf4j(topic = "payment-service")
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
        log.info("【{}】invoke pay ===============================>>>> request : {}", payRequest);
        PayResponse payResponse = pay.pay(payRequest);
        log.info("【{}】invoke pay ===============================<<<< response : {}", payResponse);
        return payResponse;
    }

    /**
     * 查询订单
     *
     * @param command
     * @return
     */
    @Override
    public R queryOrder(QueryOrderCommand command) {
        PayCommand payCommand = paymentContext.creator(command.getChannel());
        log.info("【{}】invoke queryOrder ===============================>>>> request : {}", getClass().getSimpleName(), command);
        R r = payCommand.queryOrder(command);
        log.info("【{}】invoke queryOrder ===============================<<<< response : {}", getClass().getSimpleName(),  r);
        return r;
    }

    /**
     * 申请退款
     *
     * @param command
     * @return
     */
    @Override
    public R refund(RefundCommand command) {
        PayCommand payCommand = paymentContext.creator(command.getChannel());
        log.info("【{}】invoke refund ===============================>>>> request : {}", getClass().getSimpleName(), command);
        R r = payCommand.refund(command);
        log.info("【{}】invoke refund ===============================<<<< response : {}", getClass().getSimpleName(),  r);
        return r;
    }

    /**
     * 查询申请退款订单信息
     *
     * @param command
     * @return
     */
    @Override
    public R queryRefundOrder(RefundQueryCommand command) {
        PayCommand payCommand = paymentContext.creator(command.getChannel());
        log.info("【{}】invoke queryRefundOrder ===============================>>>> request : {}", getClass().getSimpleName(), command);
        R r = payCommand.queryRefundOrder(command);
        log.info("【{}】invoke queryRefundOrder ===============================<<<< response : {}", getClass().getSimpleName(),  r);
        return r;
    }

    /**
     * 关闭订单
     *
     * @param command
     * @return
     */
    @Override
    public R closeOrder(CloseOrderCommand command) {
        PayCommand payCommand = paymentContext.creator(command.getChannel());
        log.info("【{}】invoke closeOrder ===============================>>>> request : {}", getClass().getSimpleName(), command);
        R r = payCommand.closeOrder(command);
        log.info("【{}】invoke closeOrder ===============================<<<< response : {}", getClass().getSimpleName(),  r);
        return r;
    }
}
