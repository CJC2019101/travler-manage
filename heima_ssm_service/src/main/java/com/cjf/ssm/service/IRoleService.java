package com.cjf.ssm.service;

import com.cjf.ssm.domain.Permission;
import com.cjf.ssm.domain.Role;

import java.util.List;


public interface IRoleService {

    List<Role> findAll(Integer page,Integer size) throws Exception;

    void save(Role role) throws Exception;

    Role findById(int id) throws Exception;

    List<Permission> findOtherPermission(int roleId) throws Exception;

    void addPermissionToRole(int roleId, int[] permissionsId)throws Exception;
}
