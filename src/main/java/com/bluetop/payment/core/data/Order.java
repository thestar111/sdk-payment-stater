/**
 * 文 件 名:  Order
 * 描    述:  <描述>
 * 修 改 人:  zhouping
 * 修改时间:  22:56
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bluetop.payment.core.data;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <一句话功能简述>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/5 22:56
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Builder
@Data
public class Order implements Serializable {

    /** 用户ID */
    private String uid;

    /** 交易类型 */
    private String tradeType;

    /** 交易状态  */
    private String tradeState;

    /** 银行类型 */
    private String bankType;

    /** 订单总金额 */
    private BigDecimal totalFee;

    /** 实际支付金额 */
    private BigDecimal settlementTotalFee;

    /** 货币类型 */
    private String feeType;

    /** 外部订单ID */
    private String transactionId;

    /** 系统订单ID */
    private String outTradeNo;

    /** 附加数据 */
    private String attach;

    /** 支付完成时间 */
    private String timeEnd;

    /** 交易状态描述 */
    private String tradeStateDesc;
}
