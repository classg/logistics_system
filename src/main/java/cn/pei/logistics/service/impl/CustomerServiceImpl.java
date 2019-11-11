package cn.pei.logistics.service.impl;

import cn.pei.logistics.mapper.CustomerMapper;
import cn.pei.logistics.pojo.Customer;
import cn.pei.logistics.pojo.CustomerExample;
import cn.pei.logistics.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PackageName:cn.pei.logistics.service.impl
 * @ClassName:CustomerServiceImpl
 * @Description: 实现用户接口的实现类
 * @author:CJ
 * @date:2019/10/27 15:42
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public int deleteByPrimaryKey(Long customerId) {
        return customerMapper.deleteByPrimaryKey(customerId);
    }

    @Override
    public int insert(Customer record) {
        return customerMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Customer record) {
        return customerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Customer> selectByExample(CustomerExample example) {
        return customerMapper.selectByExample(example);
    }

    @Override
    public Customer selectByPrimaryKey(Long customerId) {
        return customerMapper.selectByPrimaryKey(customerId);
    }
    

}
