package com.itheima.health.dao;

import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderMobileDao {

    //查预约设置表是否有这个日期
    OrderSetting findByOrderDate(Date orderDate);


    //通过手机号查询会员信息
    Member findByTelephone(String telephone);

    //查看Dao层 数据库 是否重复预约
    List<Order> findByCondition(Order order);


    //添加会员
    void add(Member member);

    //查询预约信息
    Map<String, Object> findById(int id);
}
