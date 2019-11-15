package com.cjf.ssm.service.impl;

import com.cjf.ssm.dao.IRoleDao;
import com.cjf.ssm.domain.Permission;
import com.cjf.ssm.domain.Role;
import com.cjf.ssm.service.IRoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional//因为是无序的 所以当我们配置 spring的事务控制的时候，只需要连接 切点和事务通知即可，
// 具体的通知类型由spring自己决定。   事务的管理已经被我们浓缩了，只有提交 和回滚 每次提交或者回滚
// 都释放资源，使用事务之前就绑定方法线程、并开启事务不自动提交。即绑定线程并开启事务。
public class RoleServiceImpl implements IRoleService {

    @Autowired
    IRoleDao roleDao;


    @Override
    public List<Role> findAll(Integer page,Integer size) throws Exception {
        //为分页查询做铺垫
        PageHelper.startPage(page,size);
        return roleDao.findAll();
    }

    @Override
    public void save(Role role) throws Exception {
        roleDao.save(role);
    }

    @Override
    public Role findById(int id) throws Exception {
        return roleDao.findById(id);
    }

    @Override
    public List<Permission> findOtherPermission(int roleId) throws Exception {
        return roleDao.findOtherPermission( roleId);
    }

    @Override
    public void addPermissionToRole(int roleId, int[] permissionsId) throws Exception {
        for (int permissionId:permissionsId
             ) {
            roleDao.addPermissionToRole(roleId,permissionId);
        }
    }
}
