package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigClientController {
    @Value("${server.port}")
    private String serverPort;  //要访问的3355上的信息

    @Value("${config.info}")
    private String configInfo;  //要访问的3355上的信息

    @GetMapping("/configInfo")
    public String configInfo(){
        return "serverPort:"+serverPort+"  configInfo:"+configInfo;
    }
}
