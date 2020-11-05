package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSttingDao;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSttingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = OrderSttingService.class)
public class OrderSttingServiceImpl implements OrderSttingService {


    @Autowired
    private OrderSttingDao orderSttingDao;

    //上传文件接收方法
    @Override
    public void add(List<OrderSetting> orderSettingsList) {

        //遍历参数
        for (OrderSetting orderSetting : orderSettingsList) {
            //取出参数里的日期,调用Dao层查数据库是否已存在
            OrderSetting osInDB =
                    orderSttingDao.findByOrderDate(orderSetting.getOrderDate());

            //判断是否为空
            if (osInDB != null) {
                //不为空,则取出他的已预约数和新表的最大预约数对比
                //已预约数比新的最大预约数大的话就报错
                if (osInDB.getReservations() > orderSetting.getNumber()) {
                    throw new HeadlessException(orderSetting.getNumber() + "该日期已有超过你的最大预约数的用户");
                }
                //已预约数比新的最大预约数小的话则修改
                orderSttingDao.updateNumber(orderSetting);

            } else {
                //为空 则直接添加到数据库中
                orderSttingDao.add(orderSetting);
            }
        }
    }




/*    //查询当前月份数据
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {

        month += "%";

        return orderSttingDao.getOrderSettingByMonth(month);

        return null;
    }*/


    //查询当前月份数据
    @Override
    public List<Map<String,Integer>> getOrderSettingByMonth(String month) {

        //将前端给的数据补全 符合数据库对应的数据
        String dateBegin = month + "-1"; //2020-10-1
        String dateEnd = month + "-31"; //2020-10-31

        //放入map中
        Map map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);

        //调用Dao层 查询
        List<OrderSetting> list = orderSttingDao.getOrderSettingByMonth(map);

        //将数据组织称List<Map> 前端所需要的样子
        List<Map<String,Integer>> data = new ArrayList<>();

        //遍历list
        for (OrderSetting orderSetting : list) {

            //创建一个map集合
            Map map1 = new HashMap();

            //将每一个数据放到每次创建的map集合中

            //获取日期 格式化 获得几号
            map1.put("date",orderSetting.getOrderDate().getDate());

            //获得预约最大数
            map1.put("number",orderSetting.getNumber());

            //获得已预约人数
            map1.put("reservations",orderSetting.getReservations());


            //再将map1放入返回的对象里
            data.add(map1);
        }
        return data;

    }


    //修改预约数
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {

        //调用Dao,查看对应的日期是否存在
        OrderSetting byOrderDate = orderSttingDao.findByOrderDate(orderSetting.getOrderDate());

        //判断
        if(byOrderDate != null){
            //不为空,则查看当前的已预约人数是否大于新的最大约人数
            if(byOrderDate.getReservations() > orderSetting.getNumber()){
                throw new MyException("最大预约数不能小于已已预约数");
            }
            //小于则直接修改
            orderSttingDao.updateNumber(orderSetting);
        }else{
            //如果不存在,直接添加
            orderSttingDao.add(orderSetting);
        }


    }


}
