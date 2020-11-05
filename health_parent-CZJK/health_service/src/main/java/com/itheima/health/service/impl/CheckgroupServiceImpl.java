package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckgroupDao;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;


@Service(interfaceClass = CheckGroupService.class)
public class CheckgroupServiceImpl implements CheckGroupService {



    @Autowired
    private CheckgroupDao checkGroupDao;


    //添加检查组
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {

        //先添加检查组
        checkGroupDao.add(checkGroup);

        //获取检查组ID
        Integer id = checkGroup.getId();

        //遍历选中的检查项
        if(null != checkitemIds){
            //添加检查组于检查项的关系
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkitemId,id);
            }
        }
    }



    //分页条件查询
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {

        //
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");

        }

        Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean.getQueryString());

        PageResult<CheckGroup> pageResult = new PageResult<>(page.getTotal(), page.getResult());


        return pageResult;
    }

    //编辑检查组
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemids) {

    }


    //先回显数据 查根据id查当前检查组信息
    @Override
    public CheckGroup findById(int id) {

        CheckGroup checkGroup = checkGroupDao.findById(id);

        return checkGroup;
    }


    // 通过检查组id查询选中的检查项id集合
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {

        List<Integer> checkitems = checkGroupDao.findCheckItemIdsByCheckGroupId(id);

        return checkitems;
    }


    //查看所有检查组
    @Override
    public List<CheckGroup> findAll() {

        List<CheckGroup> list = checkGroupDao.findAll();

        return list;
    }


    //删除检查组
    @Override
    public void deleteById(int id) {

        //先查询该检查组于检查项有没有关系,
        int cnt = checkGroupDao.findByidCheckitemCheckGroup(id);
        //判断
        if(cnt > 0){
            //如果有则报错
            throw new MyException("该检查组有检查项关联");
        }else{
            //如果没有则直接删除
            checkGroupDao.deleteById(id);
        }

    }


}


