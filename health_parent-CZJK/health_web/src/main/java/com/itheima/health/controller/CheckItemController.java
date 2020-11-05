package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;

import com.qiniu.streaming.model.ActivityRecords;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;


    //查所有
    @RequestMapping("findAll")
    public Result findAll() {

        List<CheckItem> list = checkItemService.findAll();

        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);


    }


    //新增
    @RequestMapping("add")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem) {

        boolean flag = checkItemService.add(checkItem);

        return new Result(flag, flag ? MessageConstant.ADD_CHECKGROUP_SUCCESS : MessageConstant.ADD_CHECKGROUP_FAIL);
    }


    //分页条件查询
    @PostMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {

        //调用业务来分页,获得分页结果
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);

        //返回给页面,包装到result 统一风格
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);

    }


    //删除
    @RequestMapping("deleteById")
    public Result deleteById(Integer id) {


        try {

            boolean b = checkItemService.deleteById(id);


            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }


    }


    //回显当前检查项信息
    @PostMapping("/findById")
    public Result findById(int id) {
        //调用业务仓
        CheckItem checkItem = checkItemService.findById(id);

        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);

    }


    //修改检查项
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem) {


        checkItemService.update(checkItem);

        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }


}
