package com.wxw.springbootdemo.webservice.client;

/**
 * 使用 cxf 调用 Webservice 服务
 * webservice 服务网站：http://www.webxml.com.cn/zh_cn/web_services.aspx
 */
public interface WebServiceClient {

    /**
     * Demo 测试
     * @param param
     * @return
     */
    public String myWebserviceFacadeDemo(String param);

    /**
     * 中文简体繁体转换服务
     * 可以将 method 转换为 枚举去使用
     * @param method 要调用的方法
     * @param param 要转换的参数
     * @return
     */
    public String cnTSService(String method, String param);

}
