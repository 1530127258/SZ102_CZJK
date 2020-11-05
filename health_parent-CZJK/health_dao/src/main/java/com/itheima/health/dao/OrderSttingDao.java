package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSttingDao {


    //查找日期对应的记录
    OrderSetting findByOrderDate(Date orderDate);


    //添加记录
    void add(OrderSetting orderSetting);


    //修改
    void updateNumber(OrderSetting orderSetting);


    //查询当前月份数据
    List<OrderSetting> getOrderSettingByMonth(Map map);

    //更新已预约人数
    int editReservationsByOrderDate(OrderSetting orderSetting);
}
