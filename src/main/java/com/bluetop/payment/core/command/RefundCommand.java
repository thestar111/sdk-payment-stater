/**
 * 文 件 名:  RefundEvent
 * 描    述:  <描述>
 * 修 改 人:  zhouping
 * 修改时间:  0:27
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bluetop.payment.core.command;

import com.bluetop.payment.core.cons.type.Channel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <一句话功能简述>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/4 0:27
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RefundCommand extends Command {

    /** 系统订单号 */
    private String orderId;

    /** 总金额 : 分*/
    private BigDecimal totalFee;

    /** 退款原因 */
    private String reason;

    /**
     *
     * @param channel
     */
    public RefundCommand(Channel channel) {
        super(channel);
    }
}
