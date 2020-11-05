package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSttingService;
import com.itheima.health.utils.POIUtils;
import com.sun.corba.se.impl.resolver.ORBDefaultInitRefResolverImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    //引入
    @Reference
    private OrderSttingService orderSttingService;


    //上传文件接收方法
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            //使用POI工具类读取前端上传的文件
            //一个数组代表一行数据
            List<String[]> strings = POIUtils.readExcel(excelFile);

            //创建一个实体类集合接收将要解析的文件数据
            List<OrderSetting> orderSettingsList = new ArrayList<>();


            //使用POI工具类解析文件的数据,然后存储到自己的实体类集合里
            //解析日期格式 模板的格式是==2020/09/09 ==我们要的格式是==2020-09-09

            //格式化日期
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);

            //创建一个空的Data类
            Date orderDate = null;

            //创建一个空的orderStting类
            OrderSetting os = null;

            //遍历获取出来的数组文件数据
            for (String[] dataArr : strings) {

                //取出第一个元素日期
                orderDate = sdf.parse(dataArr[0]);

                //取出第二个元素 最大可预约数
                int number = Integer.valueOf(dataArr[1]);

                //存储到自己的实体类中
                os = new OrderSetting(orderDate,number);


                //将当前的数据 每一个存储到自己的集合类中
                orderSettingsList.add(os);

            }

            //调用业务层,将获得到的数据添加到数据库中
            orderSttingService.add(orderSettingsList);

            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);

        }


    }


    //查询当前月份数据
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){

        //调用服务端查询
        //前端需要三个数据
        //月份1  预约数xx  已预约数xx
        List<Map<String,Integer>> data = orderSttingService.getOrderSettingByMonth(month);

        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,data);

    }


    //修改预约数
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){

        //调用业务层,
        orderSttingService.editNumberByDate(orderSetting);

        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);

    }
}
