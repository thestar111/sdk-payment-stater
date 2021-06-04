package com.bluetop.payment.core.strategy;

import com.bluetop.payment.core.cons.type.PayType;
import com.bluetop.payment.core.storage.LocalConfigStorage;
import com.bluetop.payment.core.strategy.request.PayRequest;
import com.bluetop.payment.core.strategy.response.PayResponse;

/**
 * <支付策略抽象类>
 *
 * @author zhouping
 * @version 1.0
 * @date 2020/9/23 2:45 下午
 * @see [相关类/方法]
 * @since JDK 1.8
 */
public abstract class PayStrategy<T extends PayRequest, PR extends PayResponse> {

    /**
     * 支付
     *
     * @param t
     * @return
     */
    public abstract PR pay(T t);

    /**
     * 支付类型
     *
     * @return
     */
    public abstract PayType payType();

    /**
     * 设置本地缓存配置
     *
     * @param localConfigStorage
     */
    public abstract void setLocalConfigStorage(LocalConfigStorage localConfigStorage);
}
