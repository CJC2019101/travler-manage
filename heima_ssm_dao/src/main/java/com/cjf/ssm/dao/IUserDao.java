package com.cjf.ssm.dao;

import com.cjf.ssm.domain.Role;
import com.cjf.ssm.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface IUserDao {

    //根据用户名称查询 用户

    @Select("select * from users where username=#{username}")
    @Results(id = "UserInfoMap" ,value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id",javaType = List.class,many = @Many(select = "com.cjf.ssm.dao.IRoleDao.findAllByUserId",fetchType = FetchType.LAZY))
    })
    public UserInfo findByUsername(String username) throws Exception;

    //查询所有用户
    @Select("select * from users")
    public List<UserInfo> findAll() throws Exception;


    //添加用户
    @Insert("insert into users(email,username,password,phoneNum,status) values(#{email},#{username},#{password},#{phoneNum},#{status})")
    public void save(UserInfo userInfo) throws Exception;

    //根据id查询用户
    @Select("select * from users where id=#{userId}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id",javaType = List.class,many = @Many(select = "com.cjf.ssm.dao.IRoleDao.findAllByUserId",fetchType = FetchType.LAZY)),
    })
    UserInfo findById(Integer userId) throws Exception;

    //根据roleId查询所有用户
    @Select("select * from users where  id in (select userId from users_role where id =#{roleId})")
    public List<UserInfo> findAllByUserId(Integer roleId) throws Exception;

    //根据用户id查询 用户信息、可以添加的角色信息。
    // 逻辑结构不正确，userInfo的roles属性只能用于封装和用户关联的角色信息
    @Select("select * from users where id =#{userId}")
    @Results( {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id",javaType = List.class,many = @Many(select = "com.cjf.ssm.dao.IRoleDao.findAllByUserId",fetchType = FetchType.LAZY))
    })
    UserInfo findUserByIdAndAllRole(int userId) throws Exception;

    //查询用户 可以添加的角色      正确的逻辑结构
    @Select("select * from role where id not in(select roleId from users_role where userId=#{userId})")
    List<Role> findOtherRoles(int userId) throws Exception;


    //给用户添加角色
    @Insert("insert into users_role(userId,roleId) values(#{userId},#{roleId})")
    void addRoleToUser(@Param("userId") int userId, @Param("roleId") int roleId) throws Exception;
}
