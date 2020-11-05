package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;


import java.util.List;

public interface CheckItemDao {


    //查所有
    List<CheckItem> findAll();

    //新增
    int add(CheckItem checkItem);


    //分页条件查询
    Page<CheckItem> findByCondition(String queryString);

    //删除
    Integer deleteById(Integer id);

    //回显当前检查项信息
    CheckItem findById(Integer id);


    //修改检查项
    void update(CheckItem checkItem);
}
