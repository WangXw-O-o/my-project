package com.wxw.springbootdemo.webservice.client.config;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceConfig {

    @Bean
    public JaxWsDynamicClientFactory createFactory() {
        return JaxWsDynamicClientFactory.newInstance();
    }

    @Bean
    public Client createCNTSClient() {
        JaxWsDynamicClientFactory factory = createFactory();
        return factory.createClient("http://www.webxml.com.cn/WebServices/TraditionalSimplifiedWebService.asmx?wsdl");
    }
}
