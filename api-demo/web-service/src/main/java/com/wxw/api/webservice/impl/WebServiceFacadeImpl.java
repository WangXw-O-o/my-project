package com.wxw.api.webservice.impl;

import com.wxw.api.webservice.WebServiceFacade;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@Service
@WebService(
        name = "WebServiceFacade",
        targetNamespace = "http://webservice.wxw.com",
        endpointInterface = "com.wxw.api.webservice.WebServiceFacade"
)
public class WebServiceFacadeImpl implements WebServiceFacade {

    @Override
    public String queryDemo(String param) {
        return "调用参数：" + param;
    }

}
