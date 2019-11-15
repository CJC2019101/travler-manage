package com.cjf.ssm.controller;

import com.cjf.ssm.domain.Permission;
import com.cjf.ssm.domain.Role;
import com.cjf.ssm.service.IRoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 角色控制器
 */

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    IRoleService roleService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,
                                @RequestParam(name = "size",required = true,defaultValue = "4") Integer size){

        ModelAndView mv=new ModelAndView();
        List<Role> roleList= null;
        try {
            roleList = roleService.findAll(page,size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //分页bean
        PageInfo Role_pageInfo=new PageInfo(roleList);
        mv.addObject("Role_pageInfo",Role_pageInfo);
        mv.setViewName("role-list");
        return mv;
    }

    @RequestMapping("/save.do")
    public String save(Role role){
        //response响应时，显示的就是固定死了的静态资源了。
        /*
        * 也不能这样说，请求转发和重定向各有优势、各有短板。response可以借助 ServletContext域
        * 实现数据的传递。
        * 但是使用三层架构web开发 使用请求映射搭配（spring的el表达式 ${pageContext.request.application}）
        * 很好的避免了请求转发的弊端 。  控制器方法之间的跳转 不能使用参数（避免请求转发的弊端），
        * 静态页面间带数据的跳转 一般都会经过控制器方法（请求转发）。 访问带数据的页面也不是直接
        * 访问，而是 通过控制器的方法 获取数据，处理数据 在请求转发至静态页面 实现数据的显示。
        * 就是将我们的静态页面很好的 数据绑定在了一起。 以前的纯Servlet是 静态页面发数据给 Servlet，
        * Servlet 处理数据 在请求转发或是重定向至一个静态页面。 当我们点击刷新是会存在 Servlet没有
        * 接收的前一个静态页面发来的数据，数据无法查询 ， 数据就没法发送给下一个静态页面。 而SpringMVC
        * 却直接将 第一个静态页面的参数 存放在URL地址中（get方式的提交），不存在说 刷新会没有参数导致
        * 数据无法显示，查询的问题。         这算是一个总结 ，以前 页面条转实在是太low了。  该多使用
        * URL请求参数这样 “请求转发”的弊端就很好的被避免了。  没有参数的控制器方法 使用不带请求参数的
        * URL请求路径即可。
        *
        * */
        try {
            roleService.save(role);
        } catch (Exception e) {
            System.out.println("添加角色失败。"+e);
        }
        return "redirect:findAll.do";
    }

    //根据角色id 查询所有可用的权限
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(
            @RequestParam(name = "id",required = true)int roleId
    ){
        ModelAndView mv=new ModelAndView();
        List<Permission> permissionList=null;
        Role role=null;
        try {
            role=roleService.findById(roleId);
            permissionList=roleService.findOtherPermission(roleId);
        } catch (Exception e) {
            System.out.println("根据角色id查询所有可用的权限失败。"+e);
        }
        mv.addObject("role",role);
        mv.addObject("permissionList",permissionList);
        mv.setViewName("role-permission-add");
        return mv;
    }

    //角色关联权限操作
    @RequestMapping("/addPermissionToRole.do")
    public String addRoleToUser(
            @RequestParam(name = "roleId",required = true)int roleId,
            @RequestParam(name = "ids",required = true)int[] permissionsId
    ){
        try {
            roleService.addPermissionToRole(roleId,permissionsId);
        } catch (Exception e) {
            System.out.println("给角色添加权限失败。"+e);
        }
        return "redirect:findAll.do";
    }
}
