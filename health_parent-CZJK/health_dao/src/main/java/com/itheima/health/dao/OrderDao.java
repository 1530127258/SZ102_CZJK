package com.itheima.health.dao;

import com.itheima.health.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    //添加已预约后的信息 订单
    void add(Order order);


    //条件查询
    List<Order> findByCondition(Order order);


    //获取订单详情
    Map findById4Detail(Integer id);

    Integer findOrderCountByDate(String date);

    Integer findOrderCountAfterDate(String date);

    Integer findVisitsCountByDate(String date);

    Integer findVisitsCountAfterDate(String date);


    //获取热门套餐
    List<Map<String, Object>> findHotSetmeal();


    //查询某段时间的预约数
    Integer findOrderCountBetweenDate(@Param("startDate") String startDate, @Param("endDate") String endDate);
}


