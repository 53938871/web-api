package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    Long insertOrder(Order order);
    List<Order> getOrdersByGuid(String guid);
    List<Order> getClientOrderByUser(String guid);
    List<Order> getPerformancesByGuid(String guid);
    int updateOrder(Order order);
    Order getOrderByIdAndGuid(Map<String, Object> paramMap);
    Order getOrderById(Long id);
    List<Order> getOrders(Map<String, Object> paramMap);
    List<Order> findOrderByGuidAndProjectId(Map<String, Object> map);
    BigDecimal computePrice(@Param("month") int month, @Param("guid") String guid);
}
