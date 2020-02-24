package com.wjl.alipaydemo.web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.wjl.alipaydemo.bean.AlipayBean;
import com.wjl.alipaydemo.config.alipay.AlipayConfig;
import com.wjl.alipaydemo.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "alipay")
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private AlipayConfig alipayConfig;

    //异步回调
    @RequestMapping(value = "notify")
    @ResponseBody
    public String notifyMethod(HttpServletRequest request){

        Map<String ,String> param = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
            String[] value = stringEntry.getValue();
            String key = stringEntry.getKey();
            //拼装验证信息
            String str = "";
            for (int i = 0; i < value.length; i++) {
                str = (i==value.length-1)?str + value[i]:str + value[i]+",";
            }
            param.put(key,str);
        }
        System.out.println("回调结果"+param.toString());
        param.remove("sign_type");
        try {
            boolean result = AlipaySignature.rsaCheckV2(param,alipayConfig.getAlipayPublicKey(),"UTF-8",alipayConfig.getSignType());
            System.out.println("验证结果："+result);

            /**
             * 自行验证业务的正确性，价格、订单号、商品信息等等
             */

            if (result){
                return "success";
            }else {
                return "failure";
            }

        } catch (AlipayApiException e) {
            return "msg:"+e.getMessage();
        }


    }

    //同步返回
    @RequestMapping(value = "returns")
    @ResponseBody
    public String returnMethod(HttpServletRequest request){

        Map<String ,String> param = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
            String[] value = stringEntry.getValue();
            String key = stringEntry.getKey();
            //拼装验证信息
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < value.length; i++) {
                sb.append((i==value.length-1)?value[i]:value[i]+",");
            }
            param.put(key,sb.toString());
        }
        System.out.println("同步结果："+param.toString());

        //如果是前后端分离的话
        //可以直接返回一段js让前端页面跳转到指定信息页
        return "支付成功!";
    }

    //创建支付
    @RequestMapping(value = "orderpay")
    @ResponseBody
    public Map<String,Object> alipayMethod(@RequestParam(name = "outTradeNo") String outTradeNo
            ,@RequestParam(name = "subject") String subject
            ,@RequestParam(name = "totalAmount")String totalAmount
            ,@RequestParam(name = "body")String body){
        Map<String,Object> modelMap = new HashMap<>();
        String result = "";
        try {
            result = alipayService.tradePrecreate(new AlipayBean(outTradeNo, subject, totalAmount, body));
            modelMap.put("success",true);
        } catch (AlipayApiException e) {
            modelMap.put("success",false);
            e.printStackTrace();
        }
        modelMap.put("result",result);
        return modelMap;
    }

}
