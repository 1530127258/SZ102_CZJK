package com.itheima.health.service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class MyTest {
    public static void main(String[] args) throws IOException {

        //TODO 填写提供者注册的配置文件
        new ClassPathXmlApplicationContext("applicationContext-service.xml");

        System.in.read();


    }



}
