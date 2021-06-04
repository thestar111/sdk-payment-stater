/**
 * 文 件 名:  PayQrCodeRequest
 * 描    述:  <描述>
 * 修 改 人:  zhouping
 * 修改时间:  15:28
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bluetop.payment.core.pay.request;

import com.bluetop.payment.core.strategy.request.PayRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;
import java.util.StringJoiner;

/**
 * <二维码支付>
 *
 * @author zhouping
 * @version 1.0
 * @date 2021/5/30 15:28
 * @see [相关类/方法]
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayQrCodeRequest extends PayRequest {

    /** trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付。 */
    private String codeUrl;

    /** 二维码logo图片 */
    private File logoFile;

    /** 二维码边长：默认400 */
    private Integer sideLength;

    @Override
    public String toString() {
        return new StringJoiner(", ", PayQrCodeRequest.class.getSimpleName() + "[", "]")
                .add("codeUrl='" + codeUrl + "'")
                .add("logoFile=" + logoFile)
                .add("sideLength=" + sideLength)
                .add("super=" + super.toString())
                .toString();
    }
}
