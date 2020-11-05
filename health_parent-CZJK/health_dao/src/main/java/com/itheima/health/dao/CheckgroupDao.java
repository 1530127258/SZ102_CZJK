package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckgroupDao {


    //添加检查组
    void add(CheckGroup checkGroup);

    //添加检查组与检查项的关系
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);



    //检查组分页查询
    Page<CheckGroup> findPage(String queryString);


    //编辑检查组
    void update(CheckGroup checkGroup);

    //先回显数据 查根据id查当前检查组信息
    CheckGroup findById(int id);

    // 通过检查组id查询选中的检查项id集合
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);


    //查看所有检查组
    List<CheckGroup> findAll();


    //查询检查组/项关系
    int findByidCheckitemCheckGroup(int id);


    //删除检查组
    void deleteById(int id);
}
