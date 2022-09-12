package com.abab.alipay;

import org.springframework.stereotype.Component;

/**
 * @author: hekai
 * @Date: 2022/6/8
 */
@Component
public class AliPayConfig {

    // 应用ID,APPID,开发时是沙箱提供的APPID
    private static final String baseUrl="http://localhost:8090/abab/";
    public static String APP_ID = "2021000120616797";

    // 商户私钥，之前所生成的密钥中的私钥
    public static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgElwsZtrkgWcESnUfc0fRpXHDt3LB9WW6RHCZ4uE54vM4Ee6wmFEXls7up+HsmHf27J0iIM8J4F4N+Gqf/91tGOf+sCJYDfUECFaOKpjMES+IaVc/3uWmhISySkGJ7ytd5dJ2/sJNTOM8hZe2LjvMYLLHnUVJZtaxPVUndmiSgs/V4l7XA7D9wpoOGXpVc277300R7WtR5E2VjkuOpo69jajSdOEpmUxlEPXqAqLeHexNDHPojqbhlkcyPyEOVm8EtSHpzL1P2RWJrg/t7FXfhL51pBZB1HKGfsG3gya0Hv21B9d81EBw8Fk0AA4Dz1UXyAP4gbRwe+jOyMPd1GgbAgMBAAECggEASwywqKTExlQhf7KFPWyoZJPkRfYR2Px8aJvEC3KDVMeib0yBE80mYB/1/5E47NwDiwMfHSwyxherIXOyUjf9kjQWOaRNRemtUemy4073et4n4hY5sRmJpMwHByynk8tOEuU5C0+gu+trsymTmvgMU2OXDt4e6S5NRKZT8X+jweTTH2RZWedqJdNLZKYHBbiwOafs2Ld18Ay1kdnLU7kK/oZwyQBi2pFpBri+yiu2Qxx1DbyKOms7UXBMrGF/gJvzG4IBoUcAPWD9FApo3EK862rwT4Y7hBuT7z1MqjMy6lbG0m9HNCfAC/qB2+iTXUm5JuSuwYEQuSo/R78OG5+8EQKBgQDS8EwTMgRBRErFl7+TRMdBwvvEzShwP1jlxmOVUX3GfPfAZRbfPNCx1buuu8hit6zYCQ+KBcrX9knLA+Mvn2Nw7oz4uu1U8piQjotvH1UQtjiDzj612MpGTJSO8Jw3J99MRqypsrhkXVYJlQuQbS4M7ZWKosG0R2NKwTj2jlykMwKBgQDCREc7eJn+EKciTapP4G15hucxplHVugjJ1y/eF5keCU2J2nlpKyAjHo1pNxaHtdg+9mrWv7POR8XF7wFoB81g4p4f3+CJVB5uhwygKxCZbrdqUG2Ox74MT13H+N3pbZd4xGklNnwJPDbCkxiFkjBJjRFTe9N+icQbofvDDp4EeQKBgQCVGW1slAoV6CPCD0VG33LItP4pWaqfzOcVY2m4feWdi2/D+rB0ExSg3ybOl8VIofP2sFDfisCabmgyrAjF/K/Zf4T9u4NObDwS0e8VGNyT93lzETGacCZqEm9fqguBoRlhXQNm4AEeUCskksmdyM6oLLdrSygIOf2hNXsCLBanqQKBgQCfCKxTyEgwkEtyrqf1WiHk8gZERAcMc46MRghexWzJBQ6gAuWPrglSFsnnT3y3Pqu355zRDwDQpzsF7cJ1+qUsYnjWWT2EzwNWsmIooPim0td+RjdaQrSXeUWp1lc8qEXvTMNS/oaOPOgLY8YYWvQC0mRTtrQODBQqFvJ23XmI6QKBgCQDgaDmrU+4rdL7SEDk6IquSw3bZL9nNQ66aPrxVXNoyI1b1uSUEqvvSY/kLbTncqy35aGcwj9t5y8/qwGNSBgnNl15pQRYhrN5XFuh6yX1dID/qUb4tgkyNNZnOP6tw186pcsCauBFr5Tx+oTicLK99ZBLIUgKfI+Uiyho1pLk";

    // 支付宝公钥,注意是APPID下的支付宝公钥，不是应用的公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk6v+z0qvmHUEeYOqkHk4eBjkj2e7l5It6bIs0LW9BdSgesPRyic+d/CNPInnRcK0ZJP5dI+rv0UJgkNpDH4sQmt1ngNU5fRQrubwOWYm9BM3l07grL3VV7u+37g5Knajy0F8Lk0HLqpwHj45+r6iQItBfXd7wBOkuiQzee0lsvQKrIfaKPJn2RhSCJaI0vyM5tARwNPtur13MBqy+Tt/vaZoZbJFMv7YdX6N3jXaGrk+lPFw4+eBW+/ApN+FqwvICsHc2eoOM168UzBtPpmxdoK3DPk/XbDPH54v53rwLRT2AyVWJaCXtXpZu0tIbdif+WZu8cukh/a6zF6vj5Xm4QIDAQAB";

    // 服务器异步通知页面路径,需要用http://格式的完整路径，不要加自定义参数，需要外网可以正常访问
    public static String notify_url = baseUrl+"alipay/notify.do";

    // 同步通知页面跳转路径 需要用http://格式的完整路径，不要加自定义参数，用来显示支付成功后返回的页面
    public static String return_url = baseUrl+"alipay/return.do";
    public static String mobile_url = baseUrl+"alipay/mobileresult.do";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 返回格式
    public static String FORMAT = "json";

}
