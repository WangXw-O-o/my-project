package com.wxw.springbootdemo.controller;

import com.wxw.drools.service.RuleService;
import com.wxw.drools.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DroolsController {

    @Autowired
    private RuleService ruleService;

    @RequestMapping("/rule")
    public String rule(){
        ruleService.rule();
        return "OK";
    }

    @RequestMapping("/rule/{price}")
    public String rule(@PathVariable("price") String price){
        Order order = new Order();
        order.setOriginalPrice(Double.valueOf(price));
        ruleService.orderRules(order);
        return "优惠后价格为：" + order.getRealPrice().toString();
    }


}
