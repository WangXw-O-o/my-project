package com.wxw.springbootdemo.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component // 注入到IOC容器中
@ConfigurationProperties(prefix = "userinfo") //标注在类上
@Data
@ToString
public class UserInfo {

    private String id;
    private int age;
    private String name;
    private boolean active;
    private Map<String, String> map;
    private Date createDate;
    private List<String> hobbies;
}
