package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;


    //添加套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {

        //添加套餐先
        setmealDao.add(setmeal);

        //从参数里获取返回的 套餐id
        Integer setmealId = setmeal.getId();

        //循环添加套餐和检查组的关系
        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId, checkgroupId);
            }
        }
    }


    //分页条件查询
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {

        //使用组件工具 分页查询
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        //判断是否有条件查询
        //如果有内容则返回false 这里取反
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        //调用Dao操作数据库
        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());

        PageResult<Setmeal> pageResult = new PageResult<Setmeal>(page.getTotal(), page.getResult());

        return pageResult;

    }


    //当前套餐数据
    @Override
    public Setmeal findById(Integer id) {

        Setmeal setmeal = setmealDao.findById(id);

        return setmeal;

    }


    //查套餐关系检查组id
    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(Integer id) {

        List<Integer> list = setmealDao.findCheckgroupIdsBySetmealId(id);

        //删除旧的关系

        return list;
    }




    //编辑套餐
    @Override
    public void update(Setmeal setmeal , Integer[] checkgroupids) {

        //先跟新套餐表
        setmealDao.update(setmeal);

        //删除旧的关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());

        //添加新的关系
        //判断是否为空
        if(checkgroupids != null){
            //遍历检查组id
            for (Integer checkgroupid : checkgroupids) {
                //添加新关系
                Integer setmealId = setmeal.getId();
                setmealDao.addSetmealCheckGroup(setmealId,checkgroupid);
            }
        }


    }


    //删除套餐
    @Override
    public void deleteById(int id) {

        //调用Dao层,查看关系表是否有关系 套餐是否有订单
        int cnt = setmealDao.findByidOrder(id);


        //判断
        if(cnt > 0){
            //如果有关系则报错
            throw new MyException("改套餐已有订单");
        }else{
            //没有 则先删除套餐与检查组的关系表的关系
            setmealDao.deleteSetmealCheckGroup(id);

            //在删除套餐
            setmealDao.deleteSetmeal(id);
        }
    }



    //获取套餐的预约数量
    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }
}
