package com.wjl.alipaydemo.bean;

import lombok.Data;

@Data
public class AlipayBean {

    /**
     * 商品订单号，必填
     */
    private String out_trade_no;

    /**
     * 订单名称，必填
     */
    private String subject;

    /**
     * 付款金额，必填
     */
    private String total_amount;

    /**
     * 商品描述，可空
     */
    private String body;

    /**
     * 超过时间参数
     */
    private String timeout_express = "10m";

    /**
     * 产品编号
     */
    private String product_code = "FAST_INSTANT_TRADE_PAY";

    public AlipayBean(String out_trade_no, String subject, String total_amount) {
        this.out_trade_no = out_trade_no;
        this.subject = subject;
        this.total_amount = total_amount;
    }

    public AlipayBean(String out_trade_no, String subject, String total_amount, String body) {
        this.out_trade_no = out_trade_no;
        this.subject = subject;
        this.total_amount = total_amount;
        this.body = body;
    }
}
