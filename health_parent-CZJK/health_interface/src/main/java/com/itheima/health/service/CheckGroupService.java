package com.itheima.health.service;

import com.github.pagehelper.Page;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;

import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;

import java.util.List;


public interface CheckGroupService {

    //添加检查组
    void add(CheckGroup checkGroup, Integer[] checkitemIds);


    //分页条件查询
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);


    //编辑检查组
    void update(CheckGroup checkGroup,Integer[] checkitemids);

    //先回显数据 查根据id查当前检查组信息
    CheckGroup findById(int id);

    // 通过检查组id查询选中的检查项id集合
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    //查看所有检查组
    List<CheckGroup> findAll();

    //删除检查组
    void deleteById(int id);
}
