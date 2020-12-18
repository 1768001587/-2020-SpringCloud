package com.atguigu.springcloud.alibaba.service.impl;

import com.atguigu.springcloud.alibaba.dao.OrderDao;
import com.atguigu.springcloud.alibaba.domin.Order;
import com.atguigu.springcloud.alibaba.service.AccountService;
import com.atguigu.springcloud.alibaba.service.OrderService;
import com.atguigu.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;

    @Override
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class) //可以处理超时异常。。。很多异常,rollbackFor指发生了异常则回滚！！！！！！！
    public void create(Order order) {
        //1.新建订单
        log.info("---------开始新建订单");
        orderDao.create(order);

        log.info("---------订单微服务开始调用库存，做扣减");
        //2.扣减库存
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("---------订单微服务开始调用库存，做扣减end");

        log.info("---------订单微服务开始调用个人账户，做扣减");
        //3.扣减账户
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("---------订单微服务开始调用个人账户，做扣减end");

        //4.修改订单状态，从0到1,1代表已经完成
        log.info("---------修改订单状态开始");
        orderDao.update(order.getUserId(),0);
        log.info("---------修改订单状态结束");

        log.info("-------下订单结束了，O(∩_∩)O哈哈~");

    }
}
