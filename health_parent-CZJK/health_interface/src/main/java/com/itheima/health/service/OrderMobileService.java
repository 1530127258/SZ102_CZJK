package com.itheima.health.service;

import com.itheima.health.pojo.Order;

import java.util.Map;

public interface OrderMobileService {


    //提交预约
    Order submitOrder(Map<String, String> orderInfo);


    //查询预约信息
    Map<String, Object> findById(int id);
}
