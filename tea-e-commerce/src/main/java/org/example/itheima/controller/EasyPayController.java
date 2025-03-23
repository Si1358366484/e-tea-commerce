package org.example.itheima.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSONObject;
import org.example.itheima.pojo.OrderDetail;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
public class EasyPayController {
    private final Config config;
    @PostMapping("/pay")
    public Map<String, Object> pay(@RequestBody OrderDetail od) throws Exception {
        String orderId = od.getOrderId();
        String totalPrice = od.getTotalPrice().toString();
        String name = "";
        if(od.getProducts().size() >= 1){
            name = od.getProducts().get(0).getName() + "等" + od.getProducts().size() + "件商品";
        }else {
            name = od.getProducts().get(0).getName();
        }
        //支付包验签，公私钥
        Factory.setOptions(config);
        //调用支付宝接口
        AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().preCreate(name, orderId, totalPrice);
        //解析结果 结果返回值为json
        String httpBody = response.getHttpBody();
        //转JSON
        JSONObject jsonObject = JSONObject.parseObject(httpBody);
        String qrUrl = jsonObject.getJSONObject("alipay_trade_precreate_response").get("qr_code").toString();
        // 生成二维码文件路径
        String fileName = orderId + ".png";
        String savePath = "C:/Users/ASUS/Desktop/tea/tea-e-commerce/file/qrcode/" + fileName;
        QrCodeUtil.generate(qrUrl, 500, 500, new File(savePath));

        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("qr_code_url", "http://2b7dcb3c.r25.cpolar.top/static/qrcode/" + fileName);// 静态资源URL
        result.put("alipay_response", jsonObject); // 保留原有支付宝响应
        return result;
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
