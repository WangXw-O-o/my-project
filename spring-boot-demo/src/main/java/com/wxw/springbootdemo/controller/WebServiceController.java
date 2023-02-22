package com.wxw.springbootdemo.controller;

import com.wxw.springbootdemo.webservice.client.WebServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebServiceController {

    @Autowired
    private WebServiceClient webServiceClient;


    @GetMapping("/webservice/test/{param}")
    public String webserviceTest(@PathVariable("param") String param) {
        return webServiceClient.myWebserviceFacadeDemo(param);
    }

    @GetMapping("/webservice/cnts/{method}/{param}")
    public String webserviceTest(@PathVariable("method") String method, @PathVariable("param") String param) {
        return "由【" + param + "】转换为：" + webServiceClient.cnTSService(method, param);
    }

}
