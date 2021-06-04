/**
 * 文 件 名:  RefundEvent
 * 描    述:  <描述>
 * 修 改 人:  zhouping
 * 修改时间:  0:27
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bluetop.payment.core.event;

import lombok.Data;

import java.io.Serializable;

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
public class RefundEvent implements Serializable {

    /** 外部交易ID */
    private String outTradeNo;

    /** 来源 */
    private String source;

    /** 退款原因 */
    private String reason;

}
