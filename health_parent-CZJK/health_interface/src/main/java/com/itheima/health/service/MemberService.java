package com.itheima.health.service;

import java.util.List;

public interface MemberService {


    //统计过去一年的会员总数
    List<Integer> getMemberReport(List<String> months);
}
