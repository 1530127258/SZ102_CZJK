package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;

import com.itheima.health.service.MemberService;
import com.itheima.health.service.ReportService;
import com.itheima.health.service.SetmealService;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.ibatis.mapping.ResultMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    //订阅业务层
    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;


    @Reference
    private ReportService reportService;

    //会员折线图
    @GetMapping("/getMemberReport")
    public Result getMemberReport(){
        //组装过去12个月的数据,前端是一个数组

        List<String> months = new ArrayList<>();

        //使用java中的 calendar操作日期,日历对象
        Calendar calendar = Calendar.getInstance();

        //设置过去一年的时间 年月日 时分秒  减去12个月
        calendar.add(Calendar.MONTH,-12);

        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        //构建12 个月的数据  循环12次
        for (int i = 0; i < 12; i++) {
            //每次增加一个月就
            calendar.add(Calendar.MONTH,1);

            //过去的日期   设置好的日期
            Date date = calendar.getTime();

            //2020-06 前端只展示年-月
            months.add(sdf.format(date));
        }

        //调用业务层  统计过去一年的会员总数
        List<Integer> memberCount = memberService.getMemberReport(months);

        //放入map
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("months",months);
        resultMap.put("memberCount",memberCount);


        //返回给前端
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,resultMap);



    }


    //套餐预约占比
    @GetMapping("/getSetmealReport")
    public Result getSetmealReport(){

        //调用业务层
        //套餐数量
        List<Map<String,Object>> setmealCount = setmealService.findSetmealCount();

        //创建套餐名称集合
        List<String> setmealNames = new ArrayList<String>();

        //从获得的参数里将套餐名称取出来
        //遍历
        for (Map<String, Object> map : setmealCount) {

            //获取套餐的名称
            setmealNames.add((String) map.get("name"));

        }

        //封装
        //创建一个Map集合
        Map<String,Object> resultMap = new HashMap<>();
        //放入套餐名称
        resultMap.put("setmealNames",setmealNames);
        //放入套餐名称和套餐数据
        resultMap.put("setmealCount",setmealCount);

        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,resultMap);
    }


    //运营统计数据
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData(){

        //调用业务层
        Map<String, Object> businessReport = reportService.getBusinessReportData();

        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS,businessReport);

    }


}
