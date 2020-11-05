package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;

import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.Query;
import java.io.IOException;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {


    private static final Logger log = LoggerFactory.getLogger(SetmealController.class);


    @Reference
    private SetmealService setmealService;


    //上传文件异步请求 upload
    @PostMapping("upload")
    public Result upload(MultipartFile imgFile) {

        //获取文件名
        String originalFilename = imgFile.getOriginalFilename();

        //截取文件名的后缀名
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));

        //产生唯一的文件名 再和后缀名拼接
        String substring1 = UUID.randomUUID().toString() + substring;

        try {
            //使用工具类将文件上传七牛云
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), substring1);

            //将产生的唯一文件名和仓库地址响应给套餐表单
            Map<String, String> resultMap = new HashMap<String, String>();

            resultMap.put("domain", QiNiuUtils.DOMAIN);

            resultMap.put("imgName", substring1);

            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, resultMap);
        } catch (IOException e) {

            log.error("上传文件失败", e);

            return new Result(true, MessageConstant.PIC_UPLOAD_FAIL);


        }


    }

    //添加套餐
    @PostMapping("add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {

        //调用业务层
        setmealService.add(setmeal, checkgroupIds);

        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);

    }


    //分页 添加查询
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {

        //调用业务层
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);

        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pageResult);
    }


    //查询当前套餐数据
    @PostMapping("/findById")
    public Result findById(Integer id){

        Setmeal setmeal = setmealService.findById(id);

        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("setmeal",setmeal);
        resultMap.put("domain",QiNiuUtils.DOMAIN);

        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,resultMap);

    }


    //查套餐关系检查组id
    @PostMapping("findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(Integer id){

        List<Integer> list = setmealService.findCheckgroupIdsBySetmealId(id);

        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);

    }




    //编辑套餐
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupids){

        setmealService.update(setmeal,checkgroupids);

        return new Result(true,"编辑套餐表成功");

    }


    //删除套餐
    @PostMapping("/deleteById")
    public Result deleteById(int id){

        //调用业务层
        setmealService.deleteById(id);

        return new Result(true,"删除套餐成功");
    }


}























