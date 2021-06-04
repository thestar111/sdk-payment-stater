/**
 * 文 件 名:  PayStrategyBean
 * 描    述:  <描述>
 * 修 改 人:  zhouping
 * 修改时间:  23:57
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bluetop.payment.core.cons.type;

import java.lang.annotation.*;

/**
 * <一句话功能简述>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/2 23:57
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PayStrategyBean {

    PayType value();
}
