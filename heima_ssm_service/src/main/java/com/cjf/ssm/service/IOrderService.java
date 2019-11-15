package com.cjf.ssm.service;

import com.cjf.ssm.domain.Orders;
import com.cjf.ssm.domain.Product;
import java.util.List;

public interface IOrderService {

    //查询所有订单
    public List<Orders> findAll(Integer page,Integer size) throws Exception;

    //根据id查询订单
    public Orders findById(Integer id) throws Exception;
}
