package com.cjf.ssm.service;

import com.cjf.ssm.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品信息的服务层
 */
public interface IProductService {

    //根据id查询产品
    public Product findById(Integer id) throws Exception;

    /**
     * 查询所有产品信息，实现分页操作
     * @param page ：网页显示的起始页
     * @param size ：每页显示数据条数
     * @return
     * @throws Exception
     */
    public List<Product> findAll(int page,int size) throws Exception;

    //添加账户
    public void save(Product product) throws Exception;
}
