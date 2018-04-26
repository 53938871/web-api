package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    Long insertOrder(Order order);
    List<Order> getOrdersByOpenId(String openId);
    List<Order> getClientOrderByUser(String openId);
    List<Order> getPerformancesByOpenId(String openId);
    int updateOrder(Order order);
    Order getOrderByIdAndOpenId(Map<String, Object> paramMap);
    Order getOrderById(Long id);
}
