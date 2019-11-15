package com.cjf.ssm.controller;

import com.cjf.ssm.domain.Permission;
import com.cjf.ssm.service.IPermissionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 资源权限操作
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    IPermissionService permissionService;

    @RequestMapping("/findAll")
    public ModelAndView findAll(
            @RequestParam(name = "page",required = true)Integer page,
            @RequestParam(name = "size",required = true)Integer size
    ){
        ModelAndView mv=new ModelAndView();
        List<Permission> permissionList=null;
        try {
            permissionList=permissionService.findAll(page,size);
        } catch (Exception e) {
            System.out.println("查询所有权限失败。"+e);
        }
        //分页bean
        PageInfo PermissionPage=new PageInfo(permissionList);
        mv.addObject("PermissionPage",PermissionPage);
        mv.setViewName("permission-list");
        return mv;
    }

    @RequestMapping("/save.do")
    public String save(Permission permission){
        try {
            permissionService.save(permission);
        } catch (Exception e) {
            System.out.println("添加资源权限失败。"+e);  //失败的话页面都不用跳转了。
        }
        return "redirect:findAll.do";
    }

}
