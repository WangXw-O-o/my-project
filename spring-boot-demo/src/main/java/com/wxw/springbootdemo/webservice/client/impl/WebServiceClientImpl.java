package com.wxw.springbootdemo.webservice.client.impl;

import com.wxw.springbootdemo.webservice.client.WebServiceClient;
import com.wxw.springbootdemo.webservice.client.config.WebServiceConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebServiceClientImpl implements WebServiceClient {

    @Autowired
    private WebServiceConfig config;

    @Override
    public String myWebserviceFacadeDemo(String param) {
        try {
            DynamicClientFactory factory = DynamicClientFactory.newInstance();
            //未使用Bean是因为在创建Client时，需要服务先存在
            Client client = factory.createClient("http://127.0.0.1:9091/webservice?wsdl");
            Object[] result = client.invoke("queryDemo", param);
            return (String) result[0];
        } catch (Exception e) {
            log.error("webservice 调用异常！");
        }
        return null;
    }

    @Override
    public String cnTSService(String method, String param) {
        try {
            Client client = config.createCNTSClient();
            Object[] result = client.invoke(method, param);
            return (String) result[0];
        } catch (Exception e) {
            log.error("webservice 调用异常！");
        }
        return null;
    }

}
