package com.cjf.ssm.dao;

import com.cjf.ssm.domain.Traveler;
import java.util.List;
import org.apache.ibatis.annotations.Select;


public interface ITravelerDao {
    //根据ordersId查询旅客
    @Select("select * from traveller WHERE id in(select travellerId from order_traveller where orderId=#{OrdersId})")
    public List<Traveler> findByOrdersId(Integer OrdersId) throws Exception;
}
