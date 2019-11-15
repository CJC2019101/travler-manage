package com.cjf.ssm.dao;

import com.cjf.ssm.domain.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ISysLogDao {

    //添加用户日志信息
    @Insert("insert into syslog(visitTime,username,ip,url,executionTime,method,EMessage) values(#{visitTime},#{username},#{ip},#{url},#{executionTime},#{method},#{EMessage})")
    void save(SysLog sysLog) throws Exception;

    //查询所有日志信息
    @Select("select * from syslog")
    List<SysLog> findAll() throws Exception;
}
