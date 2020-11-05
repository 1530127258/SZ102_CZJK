package com.itheima.health.controller;


import com.aliyuncs.exceptions.ClientException;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.utils.SMSUtils;
import com.itheima.health.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {


    @Autowired
    private JedisPool jedisPool;

    //发送手机验证码
    @PostMapping("/send4Order")
    public Result send4Order(String telephone){

        Jedis jedis = jedisPool.getResource();

        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;

        //redis中的验证码
        String codeInRedis = jedis.get(key);

        if(null == codeInRedis){
            //如果不存在,则生成验证码 发送,再把验证码存入redis中设置有效期
            //value:验证码    key:手机号
            Integer code = ValidateCodeUtils.generateValidateCode(6);

            try {
                //发送验证码
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code + "");

                //存入redis,有效期15分钟
                jedis.setex(key,15*60,code + "");

                //返回成功
                return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
            } catch (ClientException e) {
                e.printStackTrace();
                //发送失败
                return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
            }
        }
        //存在 , 验证码已经发送过 ,请注意查收
        return new Result(false, "验证码已经发送过 ,请注意查收");


    }
}
