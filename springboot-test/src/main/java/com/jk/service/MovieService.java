package com.jk.service;

import com.jk.model.Customer;

import java.util.List;

public interface MovieService {

    List queryTree(Integer id);

    List querycus(int page, int rows);

    Long querycuscount();

    void addcus(Customer customer);

    void delcus(String ids);

    void updgj(Integer id);

    void updqxgj(Integer id);

    Customer updcus(Integer id);

    void updatecus(Customer customer);
}