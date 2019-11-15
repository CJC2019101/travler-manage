package com.cjf.ssm.dao;

import com.cjf.ssm.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 产品信息的持久层
 */
public interface IProductDao {

    //根据id查询产品信息
    @Select("select * from product where id =#{id}")
    public Product findById(Integer id) throws Exception;

    /**
     * 查询所有的产品信息
     * @return
     * @throws Exception
     */
    @Select("select * from product")
    public List<Product> findAll() throws Exception;//抛编译时异常必须处理或抛出。

    //保存账户
@Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus)  values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    public void save(Product product)throws Exception;
}
