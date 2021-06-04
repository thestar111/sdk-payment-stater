package com.bluetop.payment.core.pay;

import com.bluetop.payment.core.command.QueryOrderCommand;
import com.bluetop.payment.core.command.R;
import com.bluetop.payment.core.cons.type.Channel;
import com.bluetop.payment.core.strategy.PayCommand;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
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
     * @param queryOrderCommand
     * @return
     */
    @Override
    public R queryOrder(QueryOrderCommand queryOrderCommand) {
        return null;
    }

    /**
     * 查询拒绝订单
     * @param queryOrderCommand
     * @return
     */
    @Override
    public R queryRefundOrder(QueryOrderCommand queryOrderCommand) {
        return null;
    }

    /**
     * 拒绝订单
     * @param queryOrderCommand
     * @return
     */
    @Override
    public R rufund(QueryOrderCommand queryOrderCommand) {
        return null;
    }

    /**
     * 关闭订单
     * @param queryOrderCommand
     * @return
     */
    @Override
    public R closeOrder(QueryOrderCommand queryOrderCommand) {
        return null;
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
