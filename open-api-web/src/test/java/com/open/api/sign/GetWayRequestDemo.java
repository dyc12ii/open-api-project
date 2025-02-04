package com.open.api.sign;

import cn.hutool.http.HttpUtil;
import com.alipay.api.internal.util.AlipaySignature;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 程序员小强
 */
public class GetWayRequestDemo {

    /**
     * 私钥
     */
    private static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDB0rSV7tTKlphYRYGhCQZOm7aeVicz6JfylrsbP2d5KcA19y7GsyCaZesb4PDp/c5CmIeXiZsp6XJWMlMEuHhy+jVqbY5Jm8GN59k2yGmHHhy0G3HoGkYhHCVO8lGyNqMwJjdHIXqnH4SHPTN4MF/vDYzoZmdK/ZiE+nZghlmj/TUZhUnhGDPN+QQQrIL73r6yGoA0dU5qKIbhvIwkvzO/2reSyFI0IDU1Pyvu2irzA9iFQrAJqz+LkZq7JEIhf4qEKkP378dOQWavQ9Rag0hnrWVGlMxuJN8NZT/m0ndQ/B2TqhUzdnCQugEA7wdce8+Tpq8K6lerre7jAauffR6LAgMBAAECggEBAIv3vF9V5KcUD5oXP6BqIvrbagp3zsGmoywVe7MWm4OdCeguw8HME6xME3fDflaL6cqf2bMuNTYUFnR2zQroqFrno3FjAlDXwPPYTT1JhyODNFlARIbHioNYjvyu8x5OZJRd1KdyXt+XXB5JrQSLcovwbiRZ5xf5gI3vTVMxUkSgTxf2P2smaXLZ2k6epSlvFr8u+SJaGOgjKCvbGf+jXyL0L8kntukNLocnSXU3sfFmAmd87DxPFdXAFDnS09tWOcjHfIZmwjHMX3qVP/2jj1DWOjIW0Ow/VRegbYTpLmSQTMcOUaFRprvwd0ZKaZ0aQMNPqPrqkHzrfQsnfjxY+akCgYEA+vbt40rIBDsN5HCPGbyDBU0/+A3wsGh4nqvY9JKACaMpg/FvyMz37GpL8AOMy/mUCVXjVyMoNUFZf/fEhMblBuYQBmgMVk1IQaVESvUlZ33Vgot0TU8YHY2Hpk541e3vKL0X0X6XLgS6CZ63cMx04uZxoFEWlJJm/qqLru3MQp8CgYEAxbZFVgnQ9XTQlHHgpUiS/R7qHo5joBDzlF+m29CYplI5nJUmntoChnHZ6RBoiPW58A3NJOlfIL6J2+Mwwd6kHWD2DSwVDRfk7Hb5Dw6o+tOf+In+zuPZtopr6L7oiKQtwXtGV88ZhDdqX9z5ge9EYP4Psd0Mfchv5vkJENreqJUCgYEAw8easTQHcXV4UvuURymOtLYc7zBA0f3OC0pYiAM5q0sD+hCBeg6cYmxSLT03u3BKEjZUkge1OEZwwanSPxrCVG1plvXYmgLUGZIKAsfXlDLQO3T7F8xaLcPZTN3u2kUxy4Aocp/k5Ft/nj2ZMX/ut4u6nKxlhyXm/0igi6irLlUCgYAWpalNkLRJ2Yam6mB0Llr/+ZGRzHem9yofndFMLpm9u39z6zXQTmKpqdLvOnzu607QK5SLHNxTsN+zu1NzcaBU6S1mFt2WcV08pOgkjGZYzPLvEkeIxVrD6RkxQOT7+epv1kIZftSKa5qYvoQqGRE5FwEPO6XZpqMCzxX1w0xr/QKBgDeom9MiI9a125jr/n9ghSWfvaxCXgPrdojr8QZrlo028iT711ND8QUwCbb9GDr+pyXesANCm78zhdltfeNFimEUktyS0F8Li2+GbYjTvLNXtwxTKZcOXRR+MC5bMHq4hY/+71NhnGazy3yidHn0doReezqGvkotJuRJSr+l1qmU";
    private static final String PRIVATE_KEY_RSA = "" +
            "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJfnlWSaCEXq3aDO\n" +
            "45vY6fvA+gPDorf+z/p0DF302K+c0X93O/R+MiWsxjaTIvcxcvMfNqKU2t1+B5NS\n" +
            "vz8vwWcj6QxVf2dio8wUCsu5Gw7pFRU/RTPclUGqF33EZ1Ecdkkk1exzoIceYYaE\n" +
            "NWCyRIPcSjsR0JPkh4Z9nqG1IlTlAgMBAAECgYBHw0dHhKFd7owPeU38BWjc35j9\n" +
            "iEHqLL4ycvqYtKBdrncmgbzYT3n/ZHUruS0unLTSeK+F9U7EjtKjXdct0bDK/8ji\n" +
            "C7UA6wjPcwhlCI2FXG6CWwCzCP6DGf6biUpFqFu33hBwTkuDyA31W9IYVrbhjCNL\n" +
            "+yZcmP62GMIQ/CxIwQJBAMidXMpZCP2IE4rNYDnaV4F1Bq+uSW9WqcznRRPcdGEA\n" +
            "Gf8K/H+sx9DDvws479HjCBT4h4wDSNtEIkmxlIj16jUCQQDB15buhRhrKb17X/+C\n" +
            "5yL2HeV8uG1xcLt/NhzK66Dnmpi83QRz4iKDBkXM0PsE803Un9h2ZRqwAKoElFMd\n" +
            "EJXxAkBdYOn1AdZudG1sOUZmB1yd1IQFOCHUYkltJ+T+ZqoBEJQJI+r93sWvRb3i\n" +
            "EmGGl5+Ga1xkbo72CNPQ64iQ665VAkBqGnk6bE4Wco2OV9jDrYM8ltbD8yus+ZkY\n" +
            "I+WUyW+U9tF+J7jFOdXMRIW2e/m7FvbWWPyiqer2xhZKVWo9MZPxAkBBBGxH3wad\n" +
            "/ctuDkS6SsT1b4UU99Kp6TTH+Vo0Y9Je49J9DGnkLyXWX3gx4l8pFmuf6oZ6SHBE\n" +
            "54KqpcW3vz+r\n" +
            "";


    private Map<String, Object> getObjParamMap() {
        //创建参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("app_id", "1002");
        paramMap.put("method", "open.api.test.one.method1");
        paramMap.put("version", "1.0");
        paramMap.put("api_request_id", "11111111111");
        paramMap.put("charset", "utf-8");
        paramMap.put("sign_type", "RSA2");
        paramMap.put("content", "{\"item_list\":[{\"password\":\"123\",\"real_name\":\"张三\",\"username\":\"张三\"}]}");
        return paramMap;
    }

    /**
     * PSCS8 公私钥生成方式 https://blog.csdn.net/youyangrensheng/article/details/103597619
     * 也可以直接使用支付宝开发工具生成, 文档地址: https://opendocs.alipay.com/open/00uk9e
     * @return
     */
    private Map<String, Object> getObjParamForTestMethod2() {
        //创建参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("app_id", "1002");
        paramMap.put("method", "open.api.test.one.method2");
        paramMap.put("version", "1.0");
        paramMap.put("api_request_id", "11111111111");
        paramMap.put("charset", "utf-8");
        paramMap.put("sign_type", "RSA");
        paramMap.put("content", "");
        return paramMap;
    }

    /**
     * 生成签名测试
     */
    @Test
    public void genSignTest() {
        //参数
        Map<String, Object> objParamMap = this.getObjParamForTestMethod2();
        System.out.println("加签结果:");
        System.out.println(this.getSign(objParamMap));
    }

    /**
     * Api 请求示例
     */
    @Test
    public void getWayRequestDemo() {
        //参数
        Map<String, Object> objParamMap = this.getObjParamForTestMethod2();
        //签名
        String sign = this.getSign(objParamMap);
        System.out.println("加签结果:");
        System.out.println(sign);

        //添加签名到参数
        objParamMap.put("sign", sign);
        System.out.println("入参内容：");
        System.out.println(objParamMap);
        String result = HttpUtil.post("http://localhost:8821/open/gateway", objParamMap,10000);

        System.out.println("返回结果:");
        System.out.println(result);
    }

    /**
     * 加签
     *
     * @param paramMap
     * @return
     */
    private String getSign(Map<String, Object> paramMap) {
        //map类型转换
        Map<String, String> map = new HashMap<>(paramMap.size());
        for (String s : paramMap.keySet()) {
            map.put(s, paramMap.get(s).toString());
        }

        try {
            TreeMap<String, String> signMap = new TreeMap<>();
            signMap.putAll(map);

            String charset = signMap.get("charset");
            String signType = signMap.get("sign_type");

            //待签名串
            signMap.remove("sign_type");
            signMap.remove("sign");
            String content = AlipaySignature.getSignContent(signMap);
            //签名
            return AlipaySignature.rsaSign(content, PRIVATE_KEY_RSA, charset, signType);
        } catch (Exception e) {
            throw new RuntimeException("加签异常", e);
        }

    }
}
