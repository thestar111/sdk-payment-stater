/**
 * 文 件 名:  TradeState
 * 描    述:  <描述>
 * 修 改 人:  zhouping
 * 修改时间:  22:59
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bluetop.payment.core.cons.type;

/**
 * <交易状态>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/5 22:59
 * @see [相关类/方法]
 * @since JDK 1.8
 */
public enum TradeState {

    /** 支付成功 */
    SUCCESS,

    /** 转入退款 */
    REFUND,

    /** 未支付 */
    NOTPAY,

    /** 已关闭 */
    CLOSED,

    /** 已撤销(刷卡支付) */
    REVOKED,

    /** 用户支付中 */
    USERPAYING,

    /** 支付失败(其他原因，如银行返回失败) */
    PAYERROR,

    /** 已接收，等待扣款 */
    ACCEPT,
}
