/**
 * 文 件 名:  Event
 * 描    述:  <描述>
 * 修 改 人:  zhouping
 * 修改时间:  0:35
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bluetop.payment.core.command;

import com.bluetop.payment.core.cons.type.Channel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <事件对象>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/4 0:35
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Data
public class Command implements Serializable {

    /** 外部订单编号 */
    private String outTradeNo;

    /** 渠道 */
    private Channel channel;

    /** 金额: 分 */
    private BigDecimal amount;

    /** 外部请求编号 */
    private String outRequestNo;

    /**
     *
     * @param channel
     */
    public Command(Channel channel) {
        this.channel = channel;
    }
}
