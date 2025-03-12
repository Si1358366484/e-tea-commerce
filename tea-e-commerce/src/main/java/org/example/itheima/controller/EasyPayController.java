package org.example.itheima.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@Slf4j
@AllArgsConstructor
public class EasyPayController {
    private final Config config;
    @GetMapping("/pay")
    public String pay() throws Exception {
        //支付包验签，公私钥
        Factory.setOptions(config);
        //调用支付宝接口
        AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().preCreate("iphone 15ProMax 1T", "121134567899", "112999");
        //解析结果 结果返回值为json
        String httpBody = response.getHttpBody();
        //转JSON
        JSONObject jsonObject = JSONObject.parseObject(httpBody);
        String qrUrl = jsonObject.getJSONObject("alipay_trade_precreate_response").get("qr_code").toString();
        //生成二维码
        QrCodeUtil.generate(qrUrl, 500, 500, new File("file/pay2.png"));
        return httpBody;

    }
    @PostMapping("/notify")
    public String notify(HttpServletRequest request){
        log.info("支付成功");
        String out_trade_no = request.getParameter("out_trade_no");
        log.info("订单号：{}",out_trade_no);
        //后续业务流程，发货等等
        return "success";
    }
}
