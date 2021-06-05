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

/**
 * <申请退款>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/4 0:27
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RefundQueryCommand extends Command {

    /** 系统订单号 */
    private String orderId;

    /** 系统退款单号 */
    private String refundNo;

    /** 微信退款单号 */
    private String fundId;

    public RefundQueryCommand(Channel channel) {
        super(channel);
    }
}
