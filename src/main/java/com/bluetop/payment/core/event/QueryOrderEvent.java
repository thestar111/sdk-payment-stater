/**
 * 文 件 名:  QueryOrderEvent
 * 描    述:  <描述>
 * 修 改 人:  zhouping
 * 修改时间:  0:25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bluetop.payment.core.event;

import lombok.Data;

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
public class QueryOrderEvent implements Serializable {

    /** 外部订单编号 */
    private String outTradeNo;

    /** 来源 */
    private String source;

}
