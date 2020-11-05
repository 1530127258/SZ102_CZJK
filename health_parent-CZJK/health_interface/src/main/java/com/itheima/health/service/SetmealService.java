package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {


    //添加套餐
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    //分页条件查询
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    //当前套餐数据
    Setmeal findById(Integer id);


    //查套餐关系检查组id
    List<Integer> findCheckgroupIdsBySetmealId(Integer id);


    //编辑套餐
    void update(Setmeal setmeal, Integer[] checkgroupids);


    //删除套餐
    void deleteById(int id);


    //获取套餐的预约数量
    List<Map<String, Object>> findSetmealCount();

}
