package com.itheima.health.controller.security;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Component
public class springSecurityUserService implements UserDetailsService {


    //订阅业务层
    @Reference
    private UserService userService;


    //认证授权
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //通过用户名查数据
        com.itheima.health.pojo.User userInDb = userService.findUserByUsername(username);

        //判断是否为空
        if (null != userInDb) {
            //不为空,则授权
            //userDetail是security需要的  就是这个父的返回值类型
            //根据返回值需要的参数 获取参数添加到里面
            //String username, 用户名
            //String password, 密码，必须是从数据库查询到的密码
            //Collection<? extends GrantedAuthority> authorities 用户的权限集合

            //先创建一个特别属性
            List<GrantedAuthority> authorities = new ArrayList<>();

            //获取用户的角色
            Set<Role> roles = userInDb.getRoles();
            //创建集合对象的对象
            GrantedAuthority sga = null;
            //判断角色是否为空
            if (null != roles) {
                //不为空
                //遍历角色
                for (Role role : roles) {
                    //将角色的标识取出,放入集合对象里
                    sga = new SimpleGrantedAuthority(role.getKeyword());
                    //再将放有角色标识的对象放入集合中
                    authorities.add(sga);


                    //取出这个角色下的所有权限
                    Set<Permission> permissions = role.getPermissions();
                    //判断是否为空
                    if (null != permissions) {
                        //不为空
                        //遍历所有权限
                        for (Permission permission : permissions) {
                            //将权限 标识 全部放入集合对象中
                            //遍历一次创建一次集合对象
                            //创建一次,往里面放一个权限
                            sga = new SimpleGrantedAuthority(permission.getKeyword());
                            //添加完成一个,就往集合里添加一次
                            //直到全部添加完
                            authorities.add(sga);
                        }
                    }
                }
            }
            //创建一个返回值的类
            User securityUser = new User(username, userInDb.getPassword(), authorities);
            return securityUser;

        }
        return null;
    }
}
