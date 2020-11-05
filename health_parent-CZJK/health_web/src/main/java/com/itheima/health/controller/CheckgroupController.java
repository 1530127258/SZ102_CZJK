package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;
import com.itheima.health.service.CheckItemService;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.tree.ReturnType;

import java.util.List;

@RestController
@RequestMapping("checkgroup")
public class CheckgroupController {

    @Reference
    private CheckGroupService checkGroupService;


    //添加组
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {

        checkGroupService.add(checkGroup, checkitemIds);

        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }


    //分页 条件查询
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {

        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);

        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);


    }


    //编辑检查组
    //先回显数据 查根据id查当前检查组信息
    @PostMapping("/findById")
    public Result findById(Integer id) {

        CheckGroup checkGroup = checkGroupService.findById(id);

        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
    }


    // 通过检查组id查询选中的检查项id集合
    @PostMapping("findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id) {

        List<Integer> checkitemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);

        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkitemIds);

    }


    //编辑检查组
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {

        checkGroupService.update(checkGroup, checkitemIds);

        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);

    }


    //查看所有检查组
    @PostMapping("/findAll")
    public Result findAll() {

        List<CheckGroup> list = checkGroupService.findAll();

        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
    }


    //删除检查组
    @PostMapping("/deleteById")
    public Result deleteById(int id) {


        checkGroupService.deleteById(id);

        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);

    }


}





