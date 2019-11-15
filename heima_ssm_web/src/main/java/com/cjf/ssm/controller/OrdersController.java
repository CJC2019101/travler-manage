package com.cjf.ssm.controller;

import com.cjf.ssm.domain.Orders;
import com.cjf.ssm.service.IOrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    //表现层调用业务层
    @Autowired
    private IOrderService orderService;

    /**
     *     查询所有订单信息-------未分页
     */
    /*@RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        ModelAndView mv=new ModelAndView();
        List<Orders> ordersList=null;
        try {
            //表现层调用业务层的具体体现
            ordersList=orderService.findAll();
        } catch (Exception e) {
            System.out.println("查询所有订单错误"+e);
        }
        mv.setViewName("orders-list");
        mv.addObject("ordersList",ordersList);
        return mv;
    }*/

    /**
     *     查询所有订单-------分页
     *     name ：获取指定的URL参数名称
     *     @RequestParam解决 参数必须和URL请求参数名称一致（严格区分大小写）
     *     defaultValue ：默认值
     */
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,
                                @RequestParam(name = "size",required = true,defaultValue = "4")Integer size){

        ModelAndView mv=new ModelAndView();
        List<Orders> ordersList=null;
        try {
            ordersList=orderService.findAll(page,size);
        } catch (Exception e) {
            System.out.println("-----------------------------------");
            System.out.println("查询所有订单失败"+e);
            System.out.println("-----------------------------------");
        }
        //这是一个分页bean
        PageInfo pageInfo=new PageInfo(ordersList);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("orders-page-list");
        return mv;
    }

    //根据id查询订单，查看订单信息
    @RequestMapping("/findById.do")
    @Secured("ROLE_ADMIN")
    public ModelAndView findById(@RequestParam(name = "id",required = true,defaultValue = "1")int ordersId){
        ModelAndView mv=new ModelAndView();
        Orders orders=null;
        try {
            orders=orderService.findById(ordersId);
        } catch (Exception e) {
            System.out.println("根据id查询订单错误。"+e);
        }
        mv.addObject("orders",orders);
        mv.setViewName("orders-show");
        return mv;
    }

    //根据id查询订单，修改订单信息。
    @RequestMapping("/updateById.do")
    public ModelAndView updateById(@RequestParam(name = "id",required = true,defaultValue = "1")int ordersId){
        ModelAndView mv=new ModelAndView();
        Orders orders=null;
        try {
            orders=orderService.findById(ordersId);
        } catch (Exception e) {
            System.out.println("根据id查询订单错误。"+e);
        }
        mv.addObject("orders",orders);
        mv.setViewName("orders-edit");
        return mv;
    }

}
