package cn.meilituibian.api.service;

import cn.meilituibian.api.common.ElevocIdGenerator;
import cn.meilituibian.api.domain.Order;
import cn.meilituibian.api.mapper.OrderMapper;
import cn.meilituibian.api.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProjectMapper projectMapper;

    public List<Order> getOrderByOpenIdAndProject(String openId, int projectId) {
        Map<String, Object> param = new HashMap<>();
        param.put("openId", openId);
        param.put("projectId", projectId);
        return orderMapper.findOrderByOpenIdAndProjectId(param);
    }

    public Long insertOrder(Order order) {
        String orderNo = ElevocIdGenerator.uuid32();
        order.setOrderNo(orderNo);
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

    public Order getOrderByIdAndOpenId(Long id, String openId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("openId", openId);
        return orderMapper.getOrderByIdAndOpenId(paramMap);
    }

    public List<Order> getClientOrderByUser(String openId) {
        return orderMapper.getClientOrderByUser(openId);
    }

    public Order getOrderById(Long id) {
        return orderMapper.getOrderById(id);
    }

    public Map<String, Object> getPerformancesByOpenId(String openId) {
        Map<String, Object> result = new HashMap<>();
        List<Order> list = orderMapper.getPerformancesByOpenId(openId);
        if(list == null || list.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        result.put("total", list.size());
        Set<String> openIdSet = list.stream().map(s-> s.getParentOpenId()).collect(Collectors.toSet());
        Map<String, Object> detail = new HashMap<>();
        for (String id: openIdSet) {
            List<Order> orders = new ArrayList<>();
            for (Order order : list) {
                if (id.equalsIgnoreCase(order.getParentOpenId())) {
                    orders.add(order);
                }
            }
            if (!orders.isEmpty()) {
                detail.put(id, orders);
            }
        }
        result.put("data", detail);
        return result;
    }

    public List<Order> getOrders(Map<String, Object> paramMap) {
        List<Order> list = orderMapper.getOrders(paramMap);
        return list;
    }

}
