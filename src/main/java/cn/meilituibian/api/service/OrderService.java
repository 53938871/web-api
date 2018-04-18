package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.Order;
import cn.meilituibian.api.mapper.OrderMapper;
import cn.meilituibian.api.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProjectMapper projectMapper;

    public Long insertOrder(Order order) {
        order.setCreateDate(new Date());
        Long id = orderMapper.insertOrder(order);
        projectMapper.updateBookNum(order.getProjectId());
        return order.getId();
    }

    public List<Order> getOrdersByOpenId(String openId){
        return orderMapper.getOrdersByOpenId(openId);
    }

    public boolean updateOrder(Order order){
        order.setUpdateDate(new Date());
        int count = orderMapper.updateOrder(order);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public Order getOrderById(Long id) {
        return orderMapper.getOrderById(id);
    }
}
