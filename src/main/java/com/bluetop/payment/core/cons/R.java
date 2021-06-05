package com.bluetop.payment.core.cons;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <返回结果信息>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/6/4 5:21 下午
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Data
@Builder
public class R<T> implements Serializable {

    /** 错误码 */
    private int code;

    /** 描述 */
    private String desc;

    /** 返回数据模型 */
    private T data;
}
