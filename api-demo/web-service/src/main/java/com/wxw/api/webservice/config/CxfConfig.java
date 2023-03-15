package com.wxw.api.webservice.config;

import com.wxw.api.webservice.WebServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
@Slf4j
public class CxfConfig {

    @Autowired
    private WebServiceFacade webServiceFacade;

    @Autowired
    private Bus bus;

    @Bean
    public Endpoint userServiceEndpoint() {
        String path = "http://127.0.0.1:9091/webservice";
        Endpoint endpoint = new EndpointImpl(bus, webServiceFacade);
        endpoint.publish(path);
        log.debug("WebService 服务发布：{}", path);

        return endpoint;
    }

}
