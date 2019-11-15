package com.cjf.ssm.service.impl;

import com.cjf.ssm.dao.IProductDao;
import com.cjf.ssm.domain.Product;
import com.cjf.ssm.service.IProductService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service//事务管理出现在业务层中
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Override
    public Product findById(Integer id) throws Exception {
        return productDao.findById(id);
    }

    @Override
    public List<Product> findAll(int page,int size) throws Exception {
        //page：页码。      pageSize：每页数据显示条数。
        PageHelper.startPage(page,size);
        return productDao.findAll();
    }

    @Override
    public void save(Product product) throws Exception {
        productDao.save(product);
    }
}
