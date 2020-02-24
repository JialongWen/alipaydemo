package com.wjl.alipaydemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.domain.GoodsDetail;
import com.alipay.api.domain.TradeApplyParams;
import com.alipay.api.request.AlipayOpenPublicTemplateMessageIndustryModifyRequest;
import com.alipay.api.request.AlipayTradeBatchSettleRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayOpenPublicTemplateMessageIndustryModifyResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.wjl.alipaydemo.bean.AlipayBean;
import com.wjl.alipaydemo.config.alipay.AlipayConfig;
import com.wjl.alipaydemo.service.AlipayService;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayConfig alipayConfig;

    @Override
    public String tradePrecreate(AlipayBean alipayBean) throws AlipayApiException {

        String serverUrl = alipayConfig.getOpenApiDomain();
        String appId = alipayConfig.getAppid();
        String privateKey = alipayConfig.getPrivateKey();
        String format = "json";
        String charset = "UTF-8";
        String publicKey = alipayConfig.getAlipayPublicKey();
        String signType = alipayConfig.getSignType();

        AlipayClient client = new DefaultAlipayClient(serverUrl,appId,privateKey,format,charset,publicKey,signType);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        request.setNotifyUrl(alipayConfig.getNotifyUrl());
        request.setReturnUrl(alipayConfig.getReturnUrl());

        //参数封装
        request.setBizContent(JSON.toJSONString(alipayBean));

        String result = client.pageExecute(request).getBody();
        return result;
    }
}
