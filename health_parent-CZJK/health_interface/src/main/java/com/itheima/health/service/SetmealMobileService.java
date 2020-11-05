package com.itheima.health.service;

import com.itheima.health.pojo.Setmeal;

import java.util.List;

public interface SetmealMobileService {

    //查所有
    List<Setmeal> findAll();


    //查询套餐关联的所有信息
    Setmeal findDetailById(int id);
}
