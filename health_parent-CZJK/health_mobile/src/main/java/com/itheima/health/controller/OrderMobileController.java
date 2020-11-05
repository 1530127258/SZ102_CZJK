package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.OrderMobileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderMobileController {

    @Reference
    private OrderMobileService orderMobileService;


    @Autowired
    private JedisPool jedisPool;


    //提交预约
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String, String> paraMap) {

        //验证码校验
        Jedis jedis = jedisPool.getResource();

        //取出手机号码
        String telephone = paraMap.get("telephone");

        //拼接成验证码的key
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;

        //通过key 去redis中找对应的验证码和输入的比较
        String s = jedis.get(key);

        //先判断有没有获取到值
        if (StringUtils.isEmpty(s)) {
            //没值,则重新发送
            return new Result(false, "请重新获取验证码!");
        }
        //有值  则取出前端传的作比较
        String validateCode = paraMap.get("validateCode");

        //比较
        if (!s.equals(validateCode)) {
            //不一样
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //一样
        //则移除当前的这个验证码,防止用户多次使用
        //  jedis.del(key);

        //设置预约类型
        paraMap.put("orderType", Order.ORDERTYPE_WEIXIN);

        //预约成功页面展示时需要的id
        Order order = orderMobileService.submitOrder(paraMap);


        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }


    //查询预约信息
    @GetMapping("/findById")
    public Result findById(int id){
        //调用服务查询订单信息
        Map<String,Object> orderInfo = orderMobileService.findById(id);

        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);

    }


}
