package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallBackMethod")
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }
    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
//            //设置这个线程的超时时间是5s，5s内是正常的业务逻辑，超过5s调用fallbackMethod指定的方法进行处理
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")//这里以毫秒为单位
//    })
    @HystrixCommand
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        int age = 10/0;
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }
    public String paymentInfo_TimeOutHandler(@PathVariable("id")Integer id){//会处理paymentInfo_TimeOut方法中的运行异常或者超时异常
        return "我是消费者80.对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己，o(╥﹏╥)o";
    }
    //下面是全局fallback方法
    //没有指定fallback，只写了@HystrixCommand注解则会使用全局的fallback
    public String payment_Global_FallBackMethod(){//会处理paymentInfo_TimeOut方法中的运行异常或者超时异常
        return "Global异常处理信息，请稍后再试，o(╥﹏╥)o";
    }
}
