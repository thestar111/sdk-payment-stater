/**
 * 文 件 名:  QueryOrderEvent
 * 描    述:  <描述>
 * 修 改 人:  zhouping
 * 修改时间:  0:25
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
 * <订单查询>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/4 0:25
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryOrderCommand extends Command {

    /** 系统内订单号 */
    private String orderId;

    /**
     *
     * @param channel
     */
    public QueryOrderCommand(Channel channel) {
        super(channel);
    }
}
