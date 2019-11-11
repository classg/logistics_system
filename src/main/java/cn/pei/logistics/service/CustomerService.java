package cn.pei.logistics.service;

import cn.pei.logistics.pojo.Customer;
import cn.pei.logistics.pojo.CustomerExample;

import java.util.List;

/*
该接口为用户接口
 */
public interface CustomerService {

    int deleteByPrimaryKey(Long customerId);

    int insert(Customer record);

    int updateByPrimaryKeySelective(Customer record);

    List<Customer> selectByExample(CustomerExample example);

    Customer selectByPrimaryKey(Long customerId);

}
