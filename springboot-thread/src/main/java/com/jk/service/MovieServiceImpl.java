/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MovieServiceImpl
 * Author:   ztx
 * Date:     2019/8/6 21:23
 * Description: a
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.service;

import com.jk.dao.CustomerMapper;
import com.jk.dao.TreeMapper;
import com.jk.model.Customer;
import com.jk.model.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈a〉
 *
 * @author Administrator
 * @create 2019/8/6
 * @since 1.0.0
 */
@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private TreeMapper treeMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List queryTree(Integer id) {
        if(id==null){
            id=0;
        }
        List<Tree> list = treeMapper.queryTree(id);
        for (Tree tree : list) {
            List list2=treeMapper.queryTree(tree.getId());
            if(list2.size()>0){
                tree.setState("closed");
            }
        }
        return list;
    }

    @Override
    public List querycus(int page, int rows) {
        int papa=(page-1)*rows;
        return customerMapper.querycus(papa,rows);
    }

    @Override
    public Long querycuscount() {
        return customerMapper.querycuscount();
    }

    @Override
    public void addcus(Customer customer) {
        customerMapper.addcus(customer);
    }

    @Override
    public void delcus(String ids) {
        customerMapper.delcus(ids);
    }

    @Override
    public void updgj(Integer id) {
        customerMapper.updgj(id);
    }

    @Override
    public void updqxgj(Integer id) {
        customerMapper.updqxgj(id);
    }

    @Override
    public Customer updcus(Integer id) {
        return customerMapper.updcus(id);
    }

    @Override
    public void updatecus(Customer customer) {
        customerMapper.updatecus(customer);
    }


}
