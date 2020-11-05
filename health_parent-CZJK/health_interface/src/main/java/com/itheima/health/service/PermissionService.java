package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Permission;



public interface PermissionService {


    //分页查询
    PageResult<Permission> findPage(QueryPageBean queryPageBean);


    //新增
    boolean add(Permission permission);


    //删除
    boolean deleteById(int id);

    //编辑回显
    Permission findById(int id);


    //编辑修改提交
    boolean update(Permission permission);
}
