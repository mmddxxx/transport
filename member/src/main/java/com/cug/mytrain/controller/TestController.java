package com.cug.mytrain.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope  //动态刷新注入bean中的属性，以后想换配置中心，Java代码不需要动
public class TestController {

    @Value("${test.nacos}")
    private String testNacos;

    @GetMapping("/hello")
    public String hello() {
        return String.format("Hello %s!", testNacos);
    }
}
