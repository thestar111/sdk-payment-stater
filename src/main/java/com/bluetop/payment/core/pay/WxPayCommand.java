package com.bluetop.payment.core.pay;

import cn.hutool.json.JSONUtil;
import com.bluetop.payment.core.command.*;
import com.bluetop.payment.core.cons.IErrorCode;
import com.bluetop.payment.core.cons.PaymentException;
import com.bluetop.payment.core.cons.R;
import com.bluetop.payment.core.cons.type.Channel;
import com.bluetop.payment.core.data.Order;
import com.bluetop.payment.core.strategy.PayCommand;
import com.github.binarywang.wxpay.bean.request.WxPayRefundQueryRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderCloseResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <一句话功能描述>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/4 6:09 下午
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Slf4j
public class WxPayCommand extends PayCommand {

    @Autowired
    private WxPayService wxPayService;

    /**
     * 查询订单信息
     * @param command
     * @return
     */
    @Override
    public R queryOrder(Command command) {
        QueryOrderCommand queryOrderCommand = (QueryOrderCommand) command;
        try {
            log.info("【{}】 invoke queryOrder  ==========================>>>> request ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(queryOrderCommand));
            WxPayOrderQueryResult wxPayOrderQueryResult = wxPayService.queryOrder(queryOrderCommand.getOutTradeNo(), queryOrderCommand.getOrderId());
            log.info("【{}】 invoke queryOrder  ==========================<<<< response ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(wxPayOrderQueryResult));
            if (StringUtils.equalsAny(WxPayConstants.ResultCode.FAIL, wxPayOrderQueryResult.getResultCode())) {
                R.builder().code(IErrorCode.FAIL.getCode()).desc(wxPayOrderQueryResult.getErrCodeDes()).build();
            }
            Order order = Order.builder().build();
            BeanUtils.copyProperties(wxPayOrderQueryResult, order);
            return R.builder().data(order).build();
        } catch (WxPayException e) {
            log.error("【WxPayCommand】 invoke queryOrder failed. ====================<<<< error : {}", ExceptionUtils.getRootCauseMessage(e));
            throw new PaymentException("微信查询订单失败", IErrorCode.PAYMENT_ERROR);
        }
    }

    /**
     * 查询退款信息
     * @param command
     * @return
     */
    @Override
    public R queryRefundOrder(Command command) {
        RefundQueryCommand refundQueryCommand = (RefundQueryCommand) command;
        WxPayRefundQueryRequest wxPayRefundQueryRequest = new WxPayRefundQueryRequest();
        wxPayRefundQueryRequest.setTransactionId(refundQueryCommand.getOutTradeNo());
        wxPayRefundQueryRequest.setOutTradeNo(refundQueryCommand.getOrderId());
        wxPayRefundQueryRequest.setOutRefundNo(refundQueryCommand.getRefundNo());
        wxPayRefundQueryRequest.setRefundId(refundQueryCommand.getFundId());
        try {
            log.info("【{}】 invoke queryRefundOrder  ==========================>>>> request ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(wxPayRefundQueryRequest));
            WxPayRefundQueryResult refundQueryResult = wxPayService.refundQuery(wxPayRefundQueryRequest);
            log.info("【{}】 invoke queryRefundOrder  ==========================<<<< response ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(refundQueryResult));
            return R.builder().data(refundQueryResult).build();
        } catch (WxPayException e) {
            log.error("【WxPayCommand】 invoke queryOrder failed. ====================<<<< error : {}", ExceptionUtils.getRootCauseMessage(e));
            throw new PaymentException("微信查询退款订单失败", IErrorCode.PAYMENT_ERROR);
        }
    }

    /**
     * 申请退款
     * @param command
     * @return
     */
    @Override
    public R refund(Command command) {
        RefundCommand refundCommand = (RefundCommand) command;
        WxPayRefundRequest refundRequest = new WxPayRefundRequest();
        refundRequest.setOutTradeNo(refundCommand.getOrderId());
        refundRequest.setTransactionId(refundCommand.getOutTradeNo());
        refundRequest.setRefundDesc(refundCommand.getReason());
        refundRequest.setRefundFee(refundCommand.getAmount().intValue());
        refundRequest.setTotalFee(refundCommand.getTotalFee().intValue());
        refundRequest.setOutRefundNo(refundCommand.getOutRequestNo());
        try {
            log.info("【{}】 invoke refund  ==========================>>>> request ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(refundRequest));
            WxPayRefundResult wxPayRefundResult = wxPayService.refund(refundRequest);
            log.info("【{}】 invoke refund  ==========================<<<< response ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(wxPayRefundResult));
            return R.builder().data(wxPayRefundResult).build();
        } catch (WxPayException e) {
            log.error("【WxPayCommand】 invoke queryOrder failed. ====================<<<< error : {}", ExceptionUtils.getRootCauseMessage(e));
            throw new PaymentException("微信申请退款订单失败", IErrorCode.PAYMENT_ERROR);
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
        try {
            log.info("【{}】 invoke closeOrder  ==========================>>>> request ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(closeOrderCommand));
            WxPayOrderCloseResult wxPayOrderCloseResult = wxPayService.closeOrder(closeOrderCommand.getOutTradeNo());
            log.info("【{}】 invoke closeOrder  ==========================<<<< response ：{}", getClass().getSimpleName(), JSONUtil.toJsonStr(wxPayOrderCloseResult));
            return R.builder().data(wxPayOrderCloseResult).build();
        } catch (WxPayException e) {
            log.error("【WxPayCommand】 invoke queryOrder failed. ====================<<<< error : {}", ExceptionUtils.getRootCauseMessage(e));
            throw new PaymentException("微信关闭订单失败", IErrorCode.PAYMENT_ERROR);
        }
    }

    /**
     * 渠道
     * @return
     */
    @Override
    public Channel channel() {
        return Channel.WX;
    }
}
