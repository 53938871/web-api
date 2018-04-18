package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    Long insertOrder(Order order);
    List<Order> getOrdersByOpenId(String openId);
    int updateOrder(Order order);
    Order getOrderById(Long id);
}
