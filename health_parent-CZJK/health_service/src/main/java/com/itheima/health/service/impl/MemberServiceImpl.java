package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {


    @Autowired
    private MemberDao memberDao;

    //统计过去一年的会员总数
    @Override
    public List<Integer> getMemberReport(List<String> months) {

        //
        List<Integer> memberCount = new ArrayList<Integer>();


        if(null != months){
            //循环12 次  每个月查询一下
            for (String month : months) {
                //到这个month为,一月有多少个会员
                //调用Dao
                //拼接最后的日期
                Integer count = memberDao.findMemberCountBeforeDate(month + "-31");

                memberCount.add(count);
            }
        }

        return memberCount;
    }
}
