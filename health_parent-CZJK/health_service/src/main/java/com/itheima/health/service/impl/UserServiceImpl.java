package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.UserDao;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;


    //通过用户名查询用户信息，包含角色及权限信息
    @Override
    public User findUserByUsername(String username) {

        return userDao.findByUsername(username);
    }
}
