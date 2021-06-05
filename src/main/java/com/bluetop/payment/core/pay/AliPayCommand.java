package com.bluetop.payment.core.pay;

import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundApplyModel;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.bluetop.payment.core.command.*;
import com.bluetop.payment.core.cons.IErrorCode;
import com.bluetop.payment.core.cons.PaymentException;
import com.bluetop.payment.core.cons.R;
import com.bluetop.payment.core.cons.type.Channel;
import com.bluetop.payment.core.strategy.PayCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * <支付宝支付查询>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/4 6:09 下午
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Slf4j
public class AliPayCommand extends PayCommand {

    @Autowired
    private AlipayClient alipayClient;

    /**
     * 查询订单
     * @param command
     * @return
     */
    @Override
    public R queryOrder(Command command) {
        QueryOrderCommand queryOrderCommand = (QueryOrderCommand) command;
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(queryOrderCommand.getOutTradeNo());
        alipayRequest.setBizModel(model);
        try {
            log.info("【{}】 invoke queryOrder  ==========================>>>> request ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(alipayRequest));
            AlipayResponse response = alipayClient.pageExecute(alipayRequest);
            log.info("【{}】 invoke queryOrder  ==========================<<<< response ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(response));
            if (response.isSuccess()) {
                return R.builder().data(response.getBody()).build();
            } else {
                throw new PaymentException("支付宝查询订单失败", IErrorCode.PAYMENT_ERROR);
            }
        } catch (AlipayApiException e) {
            log.error("【AliPayCommand】 invoke queryOrder failed. ====================<<<< error : {}", ExceptionUtils.getRootCauseMessage(e));
            throw new PaymentException("支付宝查询订单失败", IErrorCode.PAYMENT_ERROR);
        }
    }

    /**
     * 查询拒绝订单
     * @param command
     * @return
     */
    @Override
    public R queryRefundOrder(Command command) {
        RefundQueryCommand refundQueryCommand = (RefundQueryCommand) command;
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setOutTradeNo(refundQueryCommand.getOutTradeNo());
        model.setOutRequestNo(refundQueryCommand.getOutRequestNo());
        alipayRequest.setBizModel(model);
        try {
            log.info("【{}】 invoke queryRefundOrder  ==========================>>>> request ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(alipayRequest));
            AlipayResponse response = alipayClient.pageExecute(alipayRequest);
            log.info("【{}】 invoke queryRefundOrder  ==========================<<<< response ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(response));
            if (response.isSuccess()) {
                return R.builder().data(response.getBody()).build();
            } else {
                throw new PaymentException("支付宝查询退款订单失败", IErrorCode.PAYMENT_ERROR);
            }
        } catch (AlipayApiException e) {
            log.error("【AliPayCommand】 invoke queryRefundOrder failed. ====================<<<< error : {}", ExceptionUtils.getRootCauseMessage(e));
            throw new PaymentException("支付宝查询退款订单失败", IErrorCode.PAYMENT_ERROR);
        }
    }

    /**
     * 退款
     * @param command
     * @return
     */
    @Override
    public R refund(Command command) {
        RefundCommand refundCommand = (RefundCommand) command;
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        AlipayTradeRefundApplyModel model = new AlipayTradeRefundApplyModel();
        model.setOutTradeNo(refundCommand.getOutTradeNo());
        model.setRefundAmount(refundCommand.getAmount().divide(new BigDecimal(100)).toString());
        model.setRefundReason(refundCommand.getReason());
        model.setOutRequestNo(refundCommand.getReason());
        alipayRequest.setBizModel(model);
        try {
            log.info("【{}】 invoke refund  ==========================>>>> request ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(alipayRequest));
            AlipayResponse response = alipayClient.pageExecute(alipayRequest);
            log.info("【{}】 invoke refund  ==========================<<<< response ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(response));
            if (response.isSuccess()) {
                return R.builder().data(response.getBody()).build();
            } else {
                throw new PaymentException("支付宝退款失败", IErrorCode.PAYMENT_ERROR);
            }
        } catch (AlipayApiException e) {
            log.error("【AliPayCommand】 invoke refund failed. ====================<<<< error : {}", ExceptionUtils.getRootCauseMessage(e));
            throw new PaymentException("支付宝退款失败", IErrorCode.PAYMENT_ERROR);
        }
    }

    /**
     * 关闭订单
     * @param command
     * @return
     */
    @Override
    public R closeOrder(Command command) {
        CloseOrderCommand closeOrderCommand = (CloseOrderCommand) command;
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        model.setOutTradeNo(closeOrderCommand.getOutTradeNo());
        alipayRequest.setBizModel(model);
        try {
            log.info("【{}】 invoke closeOrder  ==========================>>>> request ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(alipayRequest));
            AlipayResponse response = alipayClient.pageExecute(alipayRequest);
            log.info("【{}】 invoke closeOrder  ==========================<<<< response ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(response));
            if (response.isSuccess()) {
                return R.builder().data(response.getBody()).build();
            } else {
                throw new PaymentException("支付宝关闭订单失败", IErrorCode.PAYMENT_ERROR);
            }
        } catch (AlipayApiException e) {
            log.error("【AliPayCommand】 invoke queryOrder failed. ====================<<<< error : {}", ExceptionUtils.getRootCauseMessage(e));
            throw new PaymentException("支付宝关闭订单失败", IErrorCode.PAYMENT_ERROR);
        }
    }

    /**
     * 渠道
     * @return
     */
    @Override
    public Channel channel() {
        return Channel.ALI;
    }
}
