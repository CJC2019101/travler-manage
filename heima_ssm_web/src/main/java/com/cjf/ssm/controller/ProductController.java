package com.cjf.ssm.controller;

import com.cjf.ssm.domain.Product;
import com.cjf.ssm.service.IProductService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    //表现层调用业务层,业务层调用持久层。
    @Autowired
    IProductService productService;



    /**
     * 不使用分页查询
     * 查询全部产品
     * @return
     * @throws Exception
     */
   /* @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {

        ModelAndView mv=new ModelAndView();
        List<Product> products=productService.findAll();

        mv.addObject("productList",products);
        mv.setViewName("product-list1");
        return mv;
    }*/

   //使用分页查询
    @RequestMapping("/findAll.do")
    @RolesAllowed("ADMIN")
    public ModelAndView findAll(
            @RequestParam(name = "page",required = true,defaultValue = "1")Integer page,
            @RequestParam(name = "size",required = true,defaultValue = "4") Integer size) throws Exception {

        ModelAndView mv=new ModelAndView();
        List<Product> products=productService.findAll(page,size);
        //分页bean类，存放分页所有的数据。
        PageInfo pPageInfo=new PageInfo(products);
        mv.addObject("pPageInfo",pPageInfo);
        mv.setViewName("product-page-list1");
        return mv;
    }

    //产品添加
    @RequestMapping("/save.do")
    public String save(Product product) throws Exception {
        productService.save(product);
        return "redirect:findAll.do";
    }


}
