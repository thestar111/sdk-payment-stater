/**
 * 文 件 名:  PaymentService
 * 描    述:  <描述>
 * 修 改 人:  zhouping
 * 修改时间:  0:26
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bluetop.payment.core.service;

import com.bluetop.payment.core.strategy.request.PayRequest;
import com.bluetop.payment.core.strategy.response.PayResponse;

/**
 * <支付服务>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/2 0:26
 * @see [相关类/方法]
 * @since JDK 1.8
 */
public interface PaymentService {

    /**
     * 第三方支付
     * @param payRequest
     * @return
     */
    PayResponse pay(PayRequest payRequest);


}
