/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MovieController
 * Author:   ztx
 * Date:     2019/8/6 21:03
 * Description: a
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.controller;

import com.jk.model.Customer;
import com.jk.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈a〉
 *
 * @author Administrator
 * @create 2019/8/6
 * @since 1.0.0
 */

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping("/toshow")
    public String  toshow(){
        return "show";
    }
    @RequestMapping("toshowstore")
    public String  toshowstore(){
        return "showstore";
    }
    @RequestMapping("toadd")
    public String  toadd(){
        return "add";
    }
    //查树
    @RequestMapping("/queryTree")
    @ResponseBody
    public List queryTree(Integer id){
        return movieService.queryTree(id);
    }

    @RequestMapping("/querycus")
    @ResponseBody
    public Map querycus(int page,int rows){
        System.out.println("0000000000000000000");
        List list=movieService.querycus(page,rows);
        Long l=movieService.querycuscount();
        Map map=new HashMap();
        map.put("rows", list);
        map.put("total", l);
        return map;
    }

    @RequestMapping("/addcus")
    @ResponseBody
    public void addcus(Customer customer){
        movieService.addcus(customer);
    }
    @RequestMapping("/updatecus")
    @ResponseBody
    public void updatecus(Customer customer){
        movieService.updatecus(customer);
    }

    @RequestMapping("/delcus")
    @ResponseBody
    public void delcus(String  ids){
        movieService.delcus(ids);
    }

    @RequestMapping("/updgj")
    @ResponseBody
    public void updgj(Integer id){
        movieService.updgj(id);
    }

    @RequestMapping("/updqxgj")
    @ResponseBody
    public void updqxgj(Integer id){
        movieService.updqxgj(id);
    }

    @RequestMapping("/updcus")
    public  String updcus(Integer id, Model model){
        Customer customer=movieService.updcus(id);
        model.addAttribute("cus",customer);
        return "upd";
    }
}
