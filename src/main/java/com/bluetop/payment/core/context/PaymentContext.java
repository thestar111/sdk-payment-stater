package com.bluetop.payment.core.context;

import com.bluetop.payment.core.cons.IErrorCode;
import com.bluetop.payment.core.cons.PaymentException;
import com.bluetop.payment.core.cons.type.PayType;
import com.bluetop.payment.core.strategy.PayStrategy;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <支付上下文>
 *
 * @author zhouping
 * @version 1.0
 * @date 2020/9/29 11:50 上午
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Slf4j
@Configuration
public class PaymentContext implements InitializingBean {

    /** 支付实例集合 */
    private static Map<PayType, PayStrategy> PAY_STRATEGY = Maps.newConcurrentMap();

    @Autowired
    private List<PayStrategy> payStrategies;

    /**
     * 获取支付方式
     * @param type
     * @return
     */
    public PayStrategy creator(String type){
        PayType payType = PayType.valueOf(type);
        PayStrategy payStrategy = PAY_STRATEGY.get(payType);
        if (Objects.isNull(payStrategy)){
            throw new PaymentException("暂不支持此支付方式", IErrorCode.INVALID_PARAMS_ERROR);
        }
        return payStrategy;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("================初始化支付实例======================");
        if (CollectionUtils.isNotEmpty(payStrategies)) {
            payStrategies.forEach(payStrategy -> {
                PAY_STRATEGY.computeIfAbsent(payStrategy.payType(), v -> payStrategy);
            });
        }
        log.info("================支付实例初始化完成======================");
    }
}
