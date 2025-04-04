package org.example.itheima.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSONObject;
import org.example.itheima.mapper.CustomerMapper;
import org.example.itheima.pojo.*;
import org.example.itheima.service.CustomerService;
import org.example.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        result.put("qr_code_url", "http://fb84542.r25.cpolar.top/static/qrcode/" + fileName);// 静态资源URL
        result.put("alipay_response", jsonObject); // 保留原有支付宝响应
        System.out.println(result);
        return result;
    }
    @PostMapping("/users/qrcode")
    public Map<String, Object> rechargeQrcode(@RequestParam("amount") Double amount, @RequestParam("userId") String userId) throws Exception{
        String totalPrice = amount.toString();
        String uuidStr = UUID.randomUUID().toString().replaceAll("[^0-9]", ""); // 去除非数字
        String numericUUID = uuidStr.substring(0, 8); // 取前8位
        String qrcodeId = userId+numericUUID;
        String name = "用户充值";
        //支付包验签，公私钥
        Factory.setOptions(config);
        //调用支付宝接口
        AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().preCreate(name, qrcodeId, totalPrice);
        //解析结果 结果返回值为json
        String httpBody = response.getHttpBody();
        //转JSON
        JSONObject jsonObject = JSONObject.parseObject(httpBody);
        String qrUrl = jsonObject.getJSONObject("alipay_trade_precreate_response").get("qr_code").toString();
        // 生成二维码文件路径
        String fileName = qrcodeId + ".png";
        String savePath = "C:/Users/ASUS/Desktop/tea/tea-e-commerce/file/qrcode/" + fileName;
        QrCodeUtil.generate(qrUrl, 500, 500, new File(savePath));

        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("qr_code_url", "http://fb84542.r25.cpolar.top/static/qrcode/" + fileName);// 静态资源URL
        result.put("alipay_response", jsonObject); // 保留原有支付宝响应
        System.out.println(result);
        return result;
    }
    @PostMapping("/notify")
    public Result notify(HttpServletRequest request){
        log.info("支付成功");
        String orderReference = request.getParameter("out_trade_no");
        //构造二维码文件路径
        String qrCodePath = "C:/Users/ASUS/Desktop/tea/tea-e-commerce/file/qrcode/" + orderReference + ".png";
        try {
            //执行文件删除（推荐使用NIO方式）
            boolean deleted = Files.deleteIfExists(Paths.get(qrCodePath));
            log.info("二维码删除结果：{}，路径：{}", deleted ? "成功" : "失败", qrCodePath);
        } catch (IOException e) {
            log.error("二维码删除异常：", e);
        }
        return Result.success("支付成功");
    }
}
