package com.atguigu.springcloud.alibaba.service;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface AccountService {

    void decrease(@Param("userId") Long userId, @Param("money") BigDecimal money);
}
