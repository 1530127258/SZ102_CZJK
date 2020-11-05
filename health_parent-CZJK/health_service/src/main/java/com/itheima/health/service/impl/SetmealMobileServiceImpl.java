package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.SetmealMobileDao;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealMobileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service(interfaceClass = SetmealMobileService.class)
public class SetmealMobileServiceImpl implements SetmealMobileService {


    @Autowired
    private SetmealMobileDao setmealMobileDao;
    //查所有
    @Override
    public List<Setmeal> findAll() {

        List<Setmeal> list = setmealMobileDao.findAll();

        return list;
    }


    //查询套餐关联的所有信息
    @Override
    public Setmeal findDetailById(int id) {

        //查询当前套餐的信息
        Setmeal setmeal = setmealMobileDao.findDetailById(id);

        //判断是否为空
        if(setmeal != null){
            //查询套餐下的检查组
            List<CheckGroup> checkGroups = setmealMobileDao.findByIdSetmeal_CheckGroup(id);

            //循环检查组,查看每个组对应的检查项
            for (CheckGroup checkGroup : checkGroups) {

                //调用Dao,查看每个组对应的检查项
                List<CheckItem> checkItems = setmealMobileDao.findCheckItemByCheckGroupId(checkGroup.getId());

                //将查出的检查项放入检查组中
                checkGroup.setCheckItems(checkItems);
            }

            //再将检查组放入套餐中
            setmeal.setCheckGroups(checkGroups);

        }
        return setmeal;
    }
}
