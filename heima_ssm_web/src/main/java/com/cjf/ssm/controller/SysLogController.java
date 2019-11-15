package com.cjf.ssm.controller;

import com.cjf.ssm.domain.SysLog;
import com.cjf.ssm.service.ISysLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    ISysLogService sysLogService;

    //查询日志信息
    @RequestMapping("findAll.do")
    public ModelAndView findAll(
            @RequestParam(name = "page",required = true)Integer page,
            @RequestParam(name = "size",required = true)Integer size
    ){
        ModelAndView mv=new ModelAndView();
        List<SysLog> sysLogList=null;
        try {
            sysLogList=sysLogService.findAll(page,size);
        } catch (Exception e) {
            System.out.println("查询所有的日志信息失败。"+e);
        }
        //使用分页bean存放分页查询到的数据
        PageInfo LogPage=new PageInfo(sysLogList);
        mv.addObject("LogPage",LogPage);
        mv.setViewName("syslog-list");
        return mv;
    }
}
