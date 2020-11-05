package com.itheima.health.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.OrderMobileDao;
import com.itheima.health.dao.OrderSttingDao;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderMobileService.class)
public class OrderMobileServiceImpl implements OrderMobileService {

    @Autowired
    private OrderMobileDao orderMobileDao;

    @Autowired
    private OrderDao orderDao;


    @Autowired
    private OrderSttingDao orderSttingDao;

    //提交预约
    @Override
    //加事务
    @Transactional
    public Order submitOrder(Map<String, String> orderInfo) {

        //通过日期查询预约设置信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //设置一个空的时间容器,
        Date orderDate = null;
        //取出前端传的时间转格式存储到容器里
        try {
            orderDate = sdf.parse(orderInfo.get("orderDate"));
        } catch (ParseException e) {
            //e.printStackTrace();
            throw new MyException("日期格式不正确，请选择正确的日期");
        }




        //根据获取到的日期,去数据库查 预约设置表是否有这个日期
        OrderSetting orderSetting = orderMobileDao.findByOrderDate(orderDate);
        //判断
        if (null == orderSetting) {
            //不存在,则报错
            throw new MyException("所选日期不能预约，请选择其它日期");
        }
        //存在则判断预约的人数是否已满
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            //如果成立则报错
            throw new MyException("选日期预约已满，请选择其它日期");
        }





        //如果没满,判断是否重复预约
        //取出手机号
        String telephone = orderInfo.get("telephone");
        //通过手机号查询会员信息
        Member member = orderMobileDao.findByTelephone(telephone);

        //创建一个会员表的类,加入判断条件
        Order order = new Order();

        //放入前端传的日期
        order.setOrderDate(orderDate);
        //放入前端传的套餐id
        order.setSetmealId(Integer.valueOf(orderInfo.get("setmealId")));

        //判断是否存在
        if (null != member) {
            //存在,则判断是否重复预约
            //查询t_order表
            //条件 orderDate=? and setmeal_id=?,member=?
            //放入会员id
            order.setMemberId(member.getId());

            //查看Dao层 数据库 是否重复预约
            List<Order> list = orderMobileDao.findByCondition(order);

            //判断查询返回的结果
            if (null != list && list.size() > 0) {
                //表示 ?????????????
                //报错.
                throw new MyException("该套餐已经预约过了，请不要重复预约");
            }

        } else {
            //不存在,则注册会员
            //创建会员表类,补全信息
            member = new Member();

            //姓名
            member.setName(orderInfo.get("name"));
            //性别
            member.setSex(orderInfo.get("sex"));
            // idCard 从前端来
            member.setIdCard(orderInfo.get("idCard"));
            // phoneNumber 从前端来
            member.setPhoneNumber(telephone);
            // regTime 系统时间
            member.setRegTime(new Date());
            // password 可以不填，也可生成一个初始密码
            member.setPassword("123456");
            // remark 自动注册
            member.setRemark("由预约而注册上来的");


            //添加会员
            orderMobileDao.add(member);
            //将新增会员id 加入会员表中
            order.setMemberId(member.getId());
        }

        //可预约
        //预约类型
        order.setOrderType(orderInfo.get("orderType"));

        //预约状态
        order.setOrderStatus(Order.ORDERSTATUS_NO);

        //添加t_order预约信息
        orderDao.add(order);

        //更新已预约人数,更新成功则返回1, 数据没有变更则返回0
        int affectedCount = orderSttingDao.editReservationsByOrderDate(orderSetting);

        //判断返回值
        if (affectedCount == 0) {
            //报错,修改失败
            throw new MyException(MessageConstant.ORDER_FULL);
        }


        //返回新添加的对象
        return order;
    }


    //查询预约信息
    @Override
    public Map<String, Object> findById(int id) {

        //调用Dao
        Map<String, Object> map = orderMobileDao.findById(id);

        return map;
    }


}
