package com.itheima.health.dao;

import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

public interface SetmealMobileDao {

    //查所有
    List<Setmeal> findAll();


    //查询套餐的所有信息
    Setmeal findDetailById(int id);

    //查询套餐下的检查组
    List<CheckGroup> findByIdSetmeal_CheckGroup(int id);


    //调用Dao,查看每个组对应的检查项
    List<CheckItem> findCheckItemByCheckGroupId(Integer id);
}
