package com.cjf.ssm.dao;

import com.cjf.ssm.domain.Member;
import org.apache.ibatis.annotations.Select;

/**
 * 会员信息的持久层
 */
public interface IMemberDao {

    //根据id查询 会员信息
    @Select("select * from member")
    public Member findById(Integer id) throws Exception;
}
