package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.PermissionDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {


    //注入Dao
    @Autowired
    private PermissionDao permissionDao;

    //分页查询
    @Override
    public PageResult<Permission> findPage(QueryPageBean queryPageBean) {

        //使用工具类分页查询  PageHelper
        //将分页的条件传给工具类 每页大小 和 当前页数
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        //判断是否有查询条件
        //使用isEmpty方法
        // TODO 判断字符串的长度是否为空，如果字符串长度为 0，则返回 true，否则返回 false
        //取反 则有数据为 true
        if( ! StringUtils.isEmpty(queryPageBean.getQueryString())){
            //模糊查询 将查询条件的字符串拼接成模糊查询
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        //将查询条件交给Dao
        Page<Permission> page = permissionDao.findBycondition(queryPageBean.getQueryString());


        //封装结果
        PageResult<Permission> pageResult = new PageResult<>(page.getTotal(), page.getResult());

        return pageResult;
    }


    //新增
    @Override
    public boolean add(Permission permission) {

        //调用Dao层
        int cnt = permissionDao.add(permission);

        return cnt > 0;

    }


    //删除
    @Override
    public boolean deleteById(int id) {

        //调用Dao
        int cnt = permissionDao.deleteById(id);

        return cnt > 0;
    }


    //编辑回显
    @Override
    public Permission findById(int id) {

        //调用Dao
        Permission permission = permissionDao.findById(id);

        return permission;
    }


    //编辑修改提交
    @Override
    public boolean update(Permission permission) {

        //调用Dao
        int cnt = permissionDao.update(permission);

        return cnt > 0;
    }
}
