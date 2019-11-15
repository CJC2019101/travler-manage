package com.cjf.ssm.service.impl;

import com.cjf.ssm.dao.IOrdersDao;
import com.cjf.ssm.domain.Orders;
import com.cjf.ssm.service.IOrderService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional//spring 的事务管理
public class OdersServiceImpl implements IOrderService {
    //业务层调用持久层
    @Autowired
    private IOrdersDao ordersDao;

    @Override
    public List<Orders> findAll(Integer page,Integer size) throws Exception {
        //pageNum：页码值       pageSize：每页显示条数
        PageHelper.startPage(page,size);
        return ordersDao.findAll();
    }

    @Override
    public Orders findById(Integer id) throws Exception {
        return ordersDao.findById(id);
    }
}
