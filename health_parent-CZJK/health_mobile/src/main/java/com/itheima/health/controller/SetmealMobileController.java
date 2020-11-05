package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealMobileService;
import com.itheima.health.utils.QiNiuUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {


    //订阅服务
    @Reference
    private SetmealMobileService setmealMobileService;


    //查所有套餐,回显
    @GetMapping("/getSetmeal")
    public Result getSetmeal() {

        //查所有套餐
        List<Setmeal> list = setmealMobileService.findAll();

        //拼接图片的全路径
        list.forEach(s -> {

            s.setImg(QiNiuUtils.DOMAIN + s.getImg());

        });

        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
    }


    //查询套餐关联的所有信息
    @GetMapping("/findDetailById")
    public Result findDetailById(int id){

        Setmeal setmeal = setmealMobileService.findDetailById(id);


        //设置图片完整路径
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());


        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);

    }

}
