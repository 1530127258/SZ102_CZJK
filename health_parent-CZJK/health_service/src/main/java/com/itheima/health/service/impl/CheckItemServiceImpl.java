package com.itheima.health.service.impl;


import com.alibaba.dubbo.config.annotation.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;


import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Transactional
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    //查所有
    @Override
    public List<CheckItem> findAll() {

        List<CheckItem> list = checkItemDao.findAll();

        return list;
    }


    //添加
    @Override
    public boolean add(CheckItem checkItem) {

        int add = checkItemDao.add(checkItem);

        return add > 0;
    }


    //分页条件查询
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        //模糊查询 拼接%
        //判断是否有查询条件
        if ( ! StringUtils.isEmpty(queryPageBean.getQueryString())) {

            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");

        }
        //将查询条件交给Dao
        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());

        //将结果封装到结果对象中
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal() , page.getResult());

        return pageResult;


    }


    //删除
    @Override
    public boolean deleteById(Integer id) {

        Integer byId = checkItemDao.deleteById(id);

        return byId > 0;
    }



    //回显当前检查项信息
    @Override
    public CheckItem findById(int id) {
        //调用Dao层
        CheckItem checkItem = checkItemDao.findById(id);

        return checkItem;

    }


    //修改检查项
    @Override
    public void update(CheckItem checkItem) {

        checkItemDao.update(checkItem);

    }
}
