package com.itheima.health.dao;

import com.itheima.health.pojo.User;

public interface UserDao {


    //通过用户名查询用户信息，包含角色及权限信息
    User findByUsername(String username);
}
