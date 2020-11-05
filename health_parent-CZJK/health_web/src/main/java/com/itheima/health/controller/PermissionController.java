package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
public class PermissionController {


    //订阅
    @Reference
    private PermissionService permissionService;



    //分页查询
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){

        //调用业务层,
        PageResult<Permission> pageResult = permissionService.findPage(queryPageBean);

        //封装
        return new Result(true,"查询成功",pageResult);

    }


    //新增
    @PostMapping("/add")
    public Result add(@RequestBody Permission permission){

        //调用业务层
        boolean flag = permissionService.add(permission);

        if(flag){
            return new Result(flag,"添加成功");
        }else{
            return new Result(flag,"添加失败");
        }


    }



    //删除
    @GetMapping("/deleteById")
    public Result deleteById(int id){
        //调用业务层
        boolean flag = permissionService.deleteById(id);

        if(flag){
            return new Result(flag,"删除成功");
        }else{
            return new Result(flag,"删除失败");
        }
    }


    //编辑回显
    @GetMapping("/findById")
    public Result findById(int id){
        //调用业务层
        Permission permission = permissionService.findById(id);

        if(null != permission){
            return new Result(true,"回显成功",permission);
        }else{
            return new Result(false,"回显失败");
        }
    }


    //编辑修改提交
    @PostMapping("/update")
    public Result update(@RequestBody Permission permission){

        System.out.println(permission);
        //调用业务层
        boolean flag = permissionService.update(permission);

        if(flag){
          return new Result(flag,"编辑成功");
        }else{
            return new Result(flag,"编辑失败");
        }
    }

}
