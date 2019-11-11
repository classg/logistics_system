package cn.pei.logistics.service;

import cn.pei.logistics.pojo.Order;
import cn.pei.logistics.pojo.OrderDetail;
import cn.pei.logistics.pojo.OrderExample;

import java.util.List;

/*
该接口为用户接口
 */
public interface OrderService {

    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int updateByPrimaryKeySelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Long orderId);

    // 通过id查询相应的商品详情
    List<OrderDetail> selectOrderDetailsByOrderId(Long orderId);
}
