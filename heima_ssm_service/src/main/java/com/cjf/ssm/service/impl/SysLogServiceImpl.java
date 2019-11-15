package com.cjf.ssm.service.impl;

import com.cjf.ssm.dao.ISysLogDao;
import com.cjf.ssm.domain.SysLog;
import com.cjf.ssm.service.ISysLogService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysLogServiceImpl implements ISysLogService {

    @Autowired
    ISysLogDao sysLogDao;

    @Override
    public void save(SysLog sysLog) throws Exception {
        sysLogDao.save(sysLog);
    }

    @Override
    public List<SysLog> findAll(Integer page,Integer size) throws Exception {
        //开启分页查询
        PageHelper.startPage(page,size);
        return sysLogDao.findAll();
    }
}
