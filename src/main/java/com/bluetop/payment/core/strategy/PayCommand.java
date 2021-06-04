package com.bluetop.payment.core.strategy;

import com.bluetop.payment.core.cons.type.Channel;
import com.bluetop.payment.core.command.QueryOrderCommand;
import com.bluetop.payment.core.command.R;

/**
 * <一句话功能描述>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/4 6:06 下午
 * @see [相关类/方法]
 * @since JDK 1.8
 */
public abstract class PayCommand {

    /**
     * 查询订单
     * @param queryOrderCommand
     * @return
     */
    public abstract R queryOrder(QueryOrderCommand queryOrderCommand);

    /**
     * 查询拒绝订单
     * @param queryOrderCommand
     * @return
     */
    public abstract R queryRefundOrder(QueryOrderCommand queryOrderCommand);

    /**
     * 拒绝订单
     * @param queryOrderCommand
     * @return
     */
    public abstract R rufund(QueryOrderCommand queryOrderCommand);

    /**
     * 关闭订单
     * @param queryOrderCommand
     * @return
     */
    public abstract R closeOrder(QueryOrderCommand queryOrderCommand);

    /**
     * 支付类型
     *
     * @return
     */
    public abstract Channel channel();
}
