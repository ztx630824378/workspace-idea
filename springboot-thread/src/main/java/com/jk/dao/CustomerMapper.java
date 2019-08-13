package com.jk.dao;

import com.jk.model.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerMapper {

    List querycus(@Param("papa") int papa, @Param("rows") int rows);

    Long querycuscount();

    void addcus(Customer customer);

    void delcus(@Param("ids") String ids);

    void updgj(Integer id);

    void updqxgj(Integer id);

    Customer updcus(Integer id);

    void updatecus(Customer customer);
}