package com.wxw.webservice;


import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * WebService 服务测试
 */
@WebService(
        name = "WebServiceFacade",
        targetNamespace = "http://webservice.wxw.com"
)
public interface WebServiceFacade {

    @WebMethod
    public String queryDemo(String param);

}
