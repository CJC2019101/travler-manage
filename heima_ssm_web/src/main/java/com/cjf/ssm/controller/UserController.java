package com.cjf.ssm.controller;

import com.cjf.ssm.domain.Role;
import com.cjf.ssm.domain.UserInfo;
import com.cjf.ssm.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

/**
 * 用户表现层
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    //查询所有用户
    //参数是Integer类型是为了方便 AOP日志信息的方法获取
    @RequestMapping("/findAll.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")//只有权限是 ROLE_ADMIN的用户才可以查询所有用户。
    public ModelAndView findAll(
            @RequestParam(name = "page",required = true,defaultValue = "1")Integer page,
            @RequestParam(name = "size",required = true,defaultValue = "4")Integer size
    ){
        ModelAndView mv=new ModelAndView();
        List<UserInfo> infoList=null;
        try {
            infoList= userService.findAll(page,size);
        } catch (Exception e) {
            System.out.println("查询用户表出错"+e);
        }
        PageInfo pageInfo_user=new PageInfo(infoList);
        mv.addObject("pageInfo_user",pageInfo_user);
        mv.setViewName("user-list");
        return mv;
    }

    //添加用户
    @RequestMapping("/save.do")
    public String save(UserInfo userInfo){
        try {
            userService.save(userInfo);
            System.out.println();
        } catch (Exception e) {
            System.out.println("添加用户失败。"+e);
        }
        return "redirect:findAll.do";
    }

    //根据id查询用户 所有信息（角色、角色权限）
    @RequestMapping("/findById.do")
    @PreAuthorize("authentication.principal.username=='小六'")   //表示只有tom可以调用该方法。
    public ModelAndView findById(@RequestParam(name = "id",required = true)Integer userId){
        ModelAndView mv=new ModelAndView();
        UserInfo user=null;
        try {
            user=userService.findById(userId);
        } catch (Exception e) {
            System.out.println("根据id查询用户失败."+e);
        }
        mv.addObject("user",user);
        mv.setViewName("user-show");
        return mv;
    }

    /*//查询用户以及用户可以添加的角色   逻辑结构不好，UserInfo里面的就该是和user关联的角色。
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(
            @RequestParam(name = "id",required = true,defaultValue = "0")int userId){
        ModelAndView mv=new ModelAndView();
        UserInfo userInfo=null;
        try {
            userInfo=userService.findUserByIdAndAllRole(userId);
        } catch (Exception e) {
            System.out.println("查询指定id："+userId+"的用户信息即可以添加的角色信息失败。"+e);
        }
        mv.addObject("",userInfo);
        mv.setViewName("user-role-add(self)");
        return mv;
    }*/

    //查询用户以及用户可以添加的角色   正确版。
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(
            @RequestParam(name = "id",required = true)int userId){
        ModelAndView mv=new ModelAndView();
        UserInfo userInfo=null;
        List<Role> roleList=null;
        try {
            userInfo=userService.findById(userId);
            roleList=userService.findOtherRoles(userId);
        } catch (Exception e) {
            System.out.println("查询指定id："+userId+"的用户信息即可以添加的角色信息失败。"+e);
        }
        mv.addObject("user",userInfo);
        mv.addObject("roleList",roleList);
        mv.setViewName("user-role-add");
        return mv;
    }

    //给用户添加角色信息
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(
            @RequestParam(name = "userId",required = true) int userId,
            @RequestParam(name = "ids",required = true)int[] rolesId
    ){
        try {
            userService.addRoleToUser(userId,rolesId);
        } catch (Exception e) {
            System.out.println("给用户添加角色失败。"+e);
        }
        return "redirect:findAll.do";
    }
}
