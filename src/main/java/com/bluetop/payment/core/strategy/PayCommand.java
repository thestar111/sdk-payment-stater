package com.bluetop.payment.core.strategy;

import com.bluetop.payment.core.command.Command;
import com.bluetop.payment.core.cons.R;
import com.bluetop.payment.core.cons.type.Channel;

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
     * @param command
     * @return
     */
    public abstract R queryOrder(Command command);

    /**
     * 退款查询
     * @param command
     * @return
     */
    public abstract R queryRefundOrder(Command command);

    /**
     * 退款
     * @param command
     * @return
     */
    public abstract R refund(Command command);

    /**
     * 关闭交易
     * @param command
     * @return
     */
    public abstract R closeOrder(Command command);

    /**
     * 支付类型
     * @return
     */
    public abstract Channel channel();
}
