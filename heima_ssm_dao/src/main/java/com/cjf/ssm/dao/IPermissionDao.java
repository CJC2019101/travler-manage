package com.cjf.ssm.dao;

import com.cjf.ssm.domain.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IPermissionDao {

    //根据 roleId查询所有的权限
    @Select("select * from permission where id in (select permissionId from role_permission where roleId=#{roleId})")
    public List<Permission> findAllByRoleId(Integer roleId) throws Exception;

    //查询所有的权限
    @Select("select * from permission")
    List<Permission> findAll() throws Exception;

    //添加权限
    @Insert("insert into permission (permissionName,url) values(#{permissionName},#{url})")
    void save(Permission permission)throws Exception;
}
