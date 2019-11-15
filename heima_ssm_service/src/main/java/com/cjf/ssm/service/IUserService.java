package com.cjf.ssm.service;

import com.cjf.ssm.domain.Role;
import com.cjf.ssm.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

//UserDetailsService：认证方法的规范。    UserDetails：封装 用户信息
public interface IUserService extends UserDetailsService {

    public List<UserInfo> findAll(Integer page, Integer size) throws Exception;

    public void save(UserInfo userInfo) throws Exception;

    UserInfo findById(Integer userId) throws Exception;

    UserInfo findUserByIdAndAllRole(int userId) throws Exception;

    List<Role> findOtherRoles(int userId) throws Exception;

    void addRoleToUser(int userId, int[] rolesId) throws Exception;
}
