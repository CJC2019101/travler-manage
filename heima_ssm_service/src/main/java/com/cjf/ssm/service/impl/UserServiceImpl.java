package com.cjf.ssm.service.impl;

import com.cjf.ssm.dao.IUserDao;
import com.cjf.ssm.domain.Role;
import com.cjf.ssm.domain.UserInfo;
import com.cjf.ssm.service.IUserService;
import com.cjf.ssm.utils.BCryptPasswordEncoderUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService{

    @Autowired
    IUserDao userDao;

    @Override
    public List<UserInfo> findAll(Integer page, Integer size) throws Exception {
        //分页查询。必须是在Dao层上写上此代码中间不能有其他任何一个代码。
        PageHelper.startPage(page,size);
        return userDao.findAll();
    }

    @Override
    public void save(UserInfo userInfo) throws Exception {
        //在Service层进行密码加密。
        userInfo.setPassword(BCryptPasswordEncoderUtils.encodePassword(userInfo.getPassword()));
        userDao.save(userInfo);
    }

    @Override
    public UserInfo findById(Integer userId) throws Exception {
        return userDao.findById(userId);
    }

    @Override
    public UserInfo findUserByIdAndAllRole(int userId) throws Exception {
        return userDao.findUserByIdAndAllRole(userId);
    }

    @Override
    public List<Role> findOtherRoles(int userId) throws Exception {
        return userDao.findOtherRoles(userId);
    }

    @Override
    public void addRoleToUser(int userId, int[] rolesId) throws Exception {
        for (int i=0;i<rolesId.length;i++){
            System.out.println("---------------------");
            System.out.println(userId);
            System.out.println(rolesId);
            System.out.println("---------------------");
            userDao.addRoleToUser(userId,rolesId[i]);
        }
    }


    // 登录操作
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo userInfo=null;
        try {
            userInfo=userDao.findByUsername(s);
        } catch (Exception e) {
            System.out.println("根据用户名称查询用户失败。"+e);
        }
        if (userInfo==null)
            return null;
        //使用数据库的 用户信息创建User对象（用于封装数据的用户信息）。 用户名、密码、权限
//        User user=new User(userInfo.getUsername(),userInfo.getPassword(),getAuthority(userInfo.getRoles()));

         User user=new User(userInfo.getUsername(),
                userInfo.getPassword(),//明文必须 加上{noop}前缀表示密码安全。
                userInfo.getStatus()==0?false:true,
                true,true,true,
                getAuthority(userInfo.getRoles()));//权限必须以“ROLE_”开头
        return user;
    }

    //作用就是返回一个List集合，集合中装入的是自定义的用户权限。
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {
        List<SimpleGrantedAuthority> list=new ArrayList<>();

        for (Role role:roles
             ) {
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
//      list.add(new SimpleGrantedAuthority("ROLE_USER"));
        return list;
    }


}
