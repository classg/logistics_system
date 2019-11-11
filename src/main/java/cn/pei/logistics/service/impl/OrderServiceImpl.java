package cn.pei.logistics.service.impl;

import cn.pei.logistics.mapper.OrderDetailMapper;
import cn.pei.logistics.mapper.OrderMapper;
import cn.pei.logistics.pojo.Order;
import cn.pei.logistics.pojo.OrderDetail;
import cn.pei.logistics.pojo.OrderDetailExample;
import cn.pei.logistics.pojo.OrderExample;
import cn.pei.logistics.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PackageName:cn.pei.logistics.service.impl
 * @ClassName:OrderServiceImpl
 * @Description: 实现用户接口的实现类
 * @author:CJ
 * @date:2019/10/27 15:42
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public int deleteByPrimaryKey(Long orderId) {
        return orderMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public int insert(Order record) {
        /**
         * 新增订单业务：思路
         * 1、插入订单同时获取订单的主见id
         * 2、插入订单明细
         *      插入之前先设置订单明细对应的订单id
         */
        System.out.println("新增前"+record);
        int row = orderMapper.insert(record);
        System.out.println("新增后"+record);
        if(row == 1) {
            // 插入明细
            List<OrderDetail> orderDetails = record.getOrderDetails();

            for (OrderDetail orderDetail : orderDetails) {
                // 设置每个订单明细的orderId
                orderDetail.setOrderId(record.getOrderId());
                // 将订单明细插入到数据库
                orderDetailMapper.insert(orderDetail);
            }
        }
        return row;
    }

    @Override
    public int updateByPrimaryKeySelective(Order record) {
        return orderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Order> selectByExample(OrderExample example) {
        return orderMapper.selectByExample(example);
    }

    @Override
    public Order selectByPrimaryKey(Long orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public List<OrderDetail> selectOrderDetailsByOrderId(Long orderId) {
        OrderDetailExample orderDetailExample = new OrderDetailExample();
        OrderDetailExample.Criteria criteria = orderDetailExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<OrderDetail> orderDetails = orderDetailMapper.selectByExample(orderDetailExample);
        return orderDetails;
    }

}
