package com.wjl.alipaydemo.service;

import com.alipay.api.AlipayApiException;
import com.wjl.alipaydemo.bean.AlipayBean;

public interface AlipayService {

    public String tradePrecreate(AlipayBean alipayBean) throws AlipayApiException;

}
