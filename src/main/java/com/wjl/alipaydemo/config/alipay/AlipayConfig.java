package com.wjl.alipaydemo.config.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {

    private String openApiDomain;

    private String mcloudApiDomain;

    private String pid;

    private String appid;

    private String privateKey;

    private String publicKey;

    private String alipayPublicKey;

    private String signType;

    private Integer maxQueryRetry;

    private Long queryuration;

    private Integer maxCancelRetry;

    private Long cancelDuration;

    private Integer heartbeatDelay;

    private Integer heartbeatDuration;

    private String notifyUrl;

    private String returnUrl;


}
