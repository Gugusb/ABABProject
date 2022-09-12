package com.abab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller_Ailpay {

    //appid
    private final String APP_ID = "2021000119625133";
    //应用私钥
    private final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCDA1Y0+IjB/z4sIhJA0sgEaNGXV9+8t0fUMhrbjHjW88tz8d7jR8ev1aFMmyDKy/cSnlsoNDLwx71GCLiNQENYTvqdcsrYLMvQkmeIYT1OWcPYSl65P0TQRDBhakVdAIXvnts7KJ/nCROGMy8qrBG8ilSlNaOkiuXgp1OPIqLe2dCkrJp1spOJ3Wfq9uyqJO5xrgx6tby3MVPLu/nJ2PtzXt62Kpcj/S9Xxxk9FXOw8cwGDStMPU471VKpEP4kMnjfpUpB9/FfQ6oDfokIaq9dehN9xSitAD6p5cFct7oG94DHai5a+ST6X+OfTv03oE2DuA7ejaJn6cDGSB4IYmZRuDGdicqmPmLEHt3Kq79+EPxBYKUcWI4zQPXlA06Kcgb8HrJntjC836svSUrewWaG32g5MlJB8e1Z+1yL75E6k6R4rBJiVW2MC5VW+p/nQiY3FLfRq4a8V+VJ9uXM1h7L8TbOHtCAQKBgQDCSljMB8VnLFMKHOrcHCZNtjnDHsRYJQYEgNhhvwx2xUIgZNhzpiDmUSwR4TOaqF+Eg5bLfC+sQboANXpP5YUyt/rqUWqQ1fmEV5US9cfiYOi5rfjETK0q6VwagYRjXe84e039Q+D8bGypY+T+uuzFcJjudfWTjBkvkOzwt57/4QKBgQCsn/BLJvO4zueqIF/A5lHdA0R3C8K9ipNjmg9LvU3/5ZXOYx//CcLk82s83C2j9rgQKBgQCNTcaW3eW3K7a6rhFzh5UPQzoX+7VFfqR0BbEFtN7Pr/spZ/X4YVqWRyvkaZbC/9frRnEAKSXLP/pN1b9F1tdMa/2iUgefi3XMJ/z4v0T4iqE58A57UEEH68JMVYuNq5kwVnnZ9FONzhpbRftJuIRAJZaqAVZ0b0PAOMoQpNSbiQKBgHtAnpTuE0QFby+7hDsnTz+qC9dyQQWH3cBOn4RQzo1DUxvyQpZjAy0Oqn/F5x6RGMQU6SrirdUQbGWcANOpp9/L3YGHUrUjlT5Ehx2nPO//yTZSTWKM+p6+XALn1DGZbTChnL/5aEZsg5R4f55wL6RYezRzhq+w4wMixTQDyFLZ";
    private final String CHARSET = "UTF-8";
    // 支付宝公钥
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3CPgueDLkfB66s9ZsEUwyUbmkRknTFVyuBG4PkKI93OTOVC457ijEKknRYi8eKYo4Wl+7z4/nRXYCxc9XBynqtnJzronKm9Wv+7sswJI6g3Qn2SdjFxWHoQDrEqXKQALv2YVqE7R+BZqTS5TkDerQI1l+Nq3m7oemztrlx+96iAR5KxYO+tTr1u3XQZmjtjlqbty50DmxRCgEqJKYEu6CD+r1vi+2SXOUKnCJzsE8vHojS+Vk5oGbZYnX6Esw2TVeiCkmQ814CBwIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "http://127.0.0.1/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private final String RETURN_URL = "http://localhost:8080/returnUrl";
}
