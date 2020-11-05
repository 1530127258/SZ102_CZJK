package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {


    //查所有
    List<CheckItem> findAll();

    //新增
    boolean add(CheckItem checkItem);


    //分页条件查询
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);



    //删除
    boolean deleteById(Integer id);

    //回显当前检查项信息
    CheckItem findById(int id);


    //修改检查项
    void update(CheckItem checkItem);





}
