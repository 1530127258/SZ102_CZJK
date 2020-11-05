package com.itheima.health.dao;

import com.github.pagehelper.Page;

import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SetmealDao {

    //添加套餐
    void add(Setmeal setmeal);

    //添加套餐和检查组关系
    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);


    //分页条件查询
    Page<Setmeal> findPage(String queryString);

    //当前套餐数据
    Setmeal findById(Integer id);

    //查套餐关系检查组id
    List<Integer> findCheckgroupIdsBySetmealId(Integer id);


    //编辑套餐
    void update(Setmeal setmeal);


    //删除旧的关系 套餐和检查组的
    void deleteSetmealCheckGroup(Integer id);


    //查看套餐与订单的关系
    int findByidOrder(int id);


    //删除套餐
    void deleteSetmeal(int id);


    //获取套餐的预约数量
    List<Map<String, Object>> findSetmealCount();

}
