/**
 * 文 件 名:  Event
 * 描    述:  <描述>
 * 修 改 人:  zhouping
 * 修改时间:  0:35
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bluetop.payment.core.event;

import lombok.Data;

import java.io.Serializable;

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
public class Event implements Serializable {

    private String outTradeNo;

    private String source;
}
