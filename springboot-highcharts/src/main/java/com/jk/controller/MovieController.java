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

import java.util.ArrayList;
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
    @RequestMapping("tobing")
    public String  tobing(){
        return "bing";
    }
    @RequestMapping("toxian")
    public String  toxian(){
        return "xian";
    }
    @RequestMapping("tozhu")
    public String  tozhu(){
        return "zhu";
    }
    @RequestMapping("tomian")
    public String  tomian(){
        return "mian";
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

    @RequestMapping("/querybing")
    @ResponseBody
    public List<Map<String,Object>> querybing(){
        List<Map<String,Object>> list=movieService.querybing();
        List<Map<String,Object>> list1=new ArrayList<>();
        for(Map<String,Object> map1:list){
            Map<String,Object> map=new HashMap<>();
            map.put("name",map1.get("小时").toString());
            map.put("y",map1.get("总个数"));
            map.put("sliced","true");
            map.put("selected","true");
            list1.add(map);
        }
        return list1;
    }

    @RequestMapping("/queryzhu")
    @ResponseBody
    public List<Map<String,Object>> queryzhu(){
        List<Map<String,Object>> list=movieService.queryzhu();
        List<Map<String,Object>> list1=new ArrayList<>();
        for(Map<String,Object> map1:list){
            Map<String,Object> map=new HashMap<>();
            Integer  object = Integer.parseInt(map1.get("星期").toString()) ;
            if(object==1){
                map.put("name","星期一");
            }else if(object==2){
                map.put("name","星期二");
            }else if(object==3){
                map.put("name","星期三");
            }else if(object==4){
                map.put("name","星期四");
            }else if(object==5){
                map.put("name","星期五");
            }else if(object==6){
                map.put("name","星期六");
            }else if(object==0){
                map.put("name","星期天");
            }
            Integer  object1 = Integer.parseInt(map1.get("总个数").toString()) ;
            int [] aa =new int[]{object1};
            map.put("data",aa);
            list1.add(map);
        }
        return list1;
    }
    @RequestMapping("/querymian")
    @ResponseBody
    public List<Map<String,Object>> querymian(){
        List<Map<String,Object>> list=movieService.querymian();
        List<Map<String,Object>> list1=new ArrayList<>();
        for(Map<String,Object> map1:list){
            Map<String,Object> map=new HashMap<>();
            if(map1.get("月份").toString().equals("January")){
                map.put("name","一月") ;
                int [] aa =new int[]{200,600,900};
                map.put("data",aa);
            }else if(map1.get("月份").toString().equals("February")){
                map.put("name","二月") ;
                int [] aa =new int[]{300,200,700};
                map.put("data",aa);
            }else if(map1.get("月份").toString().equals("March")){
                map.put("name","三月") ;
                int [] aa =new int[]{100,300,600};
                map.put("data",aa);
            }else if(map1.get("月份").toString().equals("April")){
                map.put("name","四月") ;
                int [] aa =new int[]{200,500,800};
                map.put("data",aa);
            }else if(map1.get("月份").toString().equals("May")){
                map.put("name","五月") ;
                int [] aa =new int[]{800,300,100};
                map.put("data",aa);
            }else if(map1.get("月份").toString().equals("June")){
                map.put("name","六月") ;
                int [] aa =new int[]{700,300,200};
                map.put("data",aa);
            }else if(map1.get("月份").toString().equals("July")){
                map.put("name","七月") ;
                int [] aa =new int[]{600,300,900};
                map.put("data",aa);
            }else if(map1.get("月份").toString().equals("August")){
                map.put("name","八月") ;
                int [] aa =new int[]{300,600,100};
                map.put("data",aa);
            }else if(map1.get("月份").toString().equals("September")){
                map.put("name","九月") ;
                int [] aa =new int[]{400,300,600};
                map.put("data",aa);
            }else if(map1.get("月份").toString().equals("October")){
                map.put("name","十月") ;
                int [] aa =new int[]{500,400,200};
                map.put("data",aa);
            }else if(map1.get("月份").toString().equals("November")){
                map.put("name","十一月") ;
                int [] aa =new int[]{600,100,100};
                map.put("data",aa);
            }else if(map1.get("月份").toString().equals("December")){
                map.put("name","十二月") ;
                int [] aa =new int[]{700,200,100};
                map.put("data",aa);
            }
            list1.add(map);
        }
        return list1;
    }
    @RequestMapping("queryxian")
    @ResponseBody
    public List<Map<String,Object>> queryxian(){
        List<Map<String,Object>> list= movieService.queryxian();
        List<Map<String,Object>> list1=new ArrayList<>();
        for (Map<String,Object> map1:list) {
            Map<String,Object> map=new HashMap<>();
            Integer  object = Integer.parseInt(map1.get("年份").toString()) ;
            if(object==2019){
                map.put("name","2019年") ;
                int [] aa =new int[]{200,600,900};
                map.put("data",aa);
            }else if(object==2018){
                map.put("name","2018年") ;
                int [] aa =new int[]{300,200,700};
                map.put("data",aa);
            }else if(object==2017){
                map.put("name","2017年") ;
                int [] aa =new int[]{100,300,600};
                map.put("data",aa);
            }else if(object==2016){
                map.put("name","2016年") ;
                int [] aa =new int[]{200,500,800};
                map.put("data",aa);
            }else if(object==2015){
                map.put("name","2015年") ;
                int [] aa =new int[]{800,300,100};
                map.put("data",aa);
            }else {
                map.put("name","2014年") ;
                int [] aa =new int[]{100,200,400};
                map.put("data",aa);
            }
            list1.add(map);
        }
        return list1;
    }


}
