package com.cjf.ssm.service.impl;

import com.cjf.ssm.dao.IPermissionDao;
import com.cjf.ssm.domain.Permission;
import com.cjf.ssm.service.IPermissionService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    IPermissionDao permissionDao;

    @Override
    public List<Permission> findAll(Integer page,Integer size) throws Exception {
        //开启分页查询
        PageHelper.startPage(page,size);
        return permissionDao.findAll();
    }

    @Override
    public void save(Permission permission) throws Exception {
        permissionDao.save(permission);
    }
}
