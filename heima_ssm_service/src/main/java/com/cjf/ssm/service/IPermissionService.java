package com.cjf.ssm.service;

import com.cjf.ssm.domain.Permission;

import java.util.List;

public interface IPermissionService {
    List<Permission> findAll(Integer page,Integer size) throws Exception;

    void save(Permission permission) throws Exception;
}
