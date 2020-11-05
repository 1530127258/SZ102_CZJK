package com.itheima.health.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSttingService {

    //上传文件接收方法
    void add(List<OrderSetting> orderSettingsList);

    //查询当前月份数据
    List<Map<String,Integer>> getOrderSettingByMonth(String month);



    //修改预约数
    void editNumberByDate(OrderSetting orderSetting);
}
