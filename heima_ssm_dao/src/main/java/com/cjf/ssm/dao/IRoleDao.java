package com.cjf.ssm.dao;

import com.cjf.ssm.domain.Permission;
import com.cjf.ssm.domain.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface IRoleDao {


    //根据id查询所有角色信息
    @Select("select * FROM role WHERE id IN(select roleId FROM users_role where userId=#{userId})")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = List.class,many = @Many(select = "com.cjf.ssm.dao.IPermissionDao.findAllByRoleId",fetchType = FetchType.LAZY)),
            @Result(property = "users",column = "id",javaType = List.class,many = @Many(select = "com.cjf.ssm.dao.IUserDao.findAllByUserId",fetchType = FetchType.LAZY))
    })
    public List<Role> findAllByUserId(Integer userId) throws Exception;


    //查询所有的用户信息
    @Select("select * from role")
    List<Role> findAll() throws Exception;

    //添加 角色信息
    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    void save(Role role) throws Exception;

    //查询用户可以添加的角色
    @Select("select * from role where id not in (select roleId from users_role where userId=#{userId})")
    List<Role> findNotRoleByUserId(int userId);

    //根据id查询角色
    //不推荐使用 role类的内部属性封装 与该role类对象无关联的数据。
    @Select("select * from role where id=#{roleId}")
    Role findById(int roleId) throws Exception;

    //查询和指定角色没有关联的 权限
    @Select("select * from permission where id not in(select permissionId from role_permission where roleId=#{roleId})")
    List<Permission> findOtherPermission(int roleId) throws Exception;

    // 给角色添加权限
    @Insert("insert into role_permission values(#{permissionId},#{roleId})")
    void addPermissionToRole(@Param("roleId") int roleId, @Param("permissionId") int permissionId)throws Exception;
}
