package com.cjf.ssm.dao;

import com.cjf.ssm.domain.Member;
import com.cjf.ssm.domain.Orders;
import com.cjf.ssm.domain.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

//试试不用注解 核心容器中是否存有该 代理对象
public interface IOrdersDao {


    //订单查询
    @Select("select * from orders")
    //创建 实体类属性 与 查询结果集 的对应
    @Results( id = "ordersMap-All", value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "orderNum",column = "orderNum"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "orderStatus",column = "orderStatus"),
            @Result(property = "peopleCount",column = "peopleCount"),
            @Result(property = "payType",column = "payType"),
            @Result(property = "orderDesc",column = "orderDesc"),
                    /*
                    * one对应xml配置文件方式的association
	                  Many对应 collection
	                  其他配置基本没差*/
            //One ： 表示实体表之间的对应关系是 一对一、一对多。  Many：表示实体表之间的对应关系是 一对多。
            // 延迟加载必须是有对应关系的前提下才可以使用。 SQL语句的分割，
            //javaType指定内部属性结果集的封转类，必写。
            //select：指定实体类表中对应表的实体类（对象、集合）的数据封装，mybatis框架会自动的根据实体类属性进行自动的封装。    大白话指定查询的SQL语句（持久层接口对应方法）。
            //column：指定select 指定的持久层接口实现类方法的参数。 因为是 “多表查询” 嘛。
            @Result(property = "product",column = "productId",javaType = Product.class,one = @One(select = "com.cjf.ssm.dao.IProductDao.findById",fetchType = FetchType.LAZY)),
    })
    public List<Orders> findAll() throws Exception;

    //根据id查询订单
    @Select("select * from orders where id=#{ordersId}")
    @Results(id = "ordersMap-Id",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "orderNum",column = "orderNum"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "orderStatus",column = "orderStatus"),
            @Result(property = "peopleCount",column = "peopleCount"),
            @Result(property = "payType",column = "payType"),
            @Result(property = "orderDesc",column = "orderDesc"),
            @Result(property = "product",column = "productId",javaType = Product.class,one = @One(select = "com.cjf.ssm.dao.IProductDao.findById",fetchType = FetchType.LAZY)),
            @Result(property = "member",column = "memberId",javaType = Member.class,one = @One(select = "com.cjf.ssm.dao.IMemberDao.findById",fetchType = FetchType.LAZY)),
            @Result(property = "travellers",column = "id",javaType = java.util.List.class,many = @Many(select = "com.cjf.ssm.dao.ITravelerDao.findByOrdersId",fetchType = FetchType.LAZY))
    })
    public Orders findById(Integer ordersId) throws Exception;

    //更新订单
    @Update("update table orders set orderNum=#{orderNum}, orderTime=#{orderTime}, orderStatus=#{orderStatus}, peopleCount=#{peopleCount},payType=#{payType},orderDesc=#{orderDesc} where id=${id}")
    public int updateById(Orders orders) throws Exception;
}
