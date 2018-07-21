package cn.meilituibian.api.service;

import cn.meilituibian.api.common.OrderNoGenerator;
import cn.meilituibian.api.domain.Order;
import cn.meilituibian.api.domain.WxUser;
import cn.meilituibian.api.mapper.OrderMapper;
import cn.meilituibian.api.mapper.ProjectMapper;
import cn.meilituibian.api.mapper.SalesManGradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private SalesManGradeMapper salesManGradeMapper;

    public List<Order> getOrderByOpenIdAndProject(String openId, int projectId) {
        Map<String, Object> param = new HashMap<>();
        param.put("openId", openId);
        param.put("projectId", projectId);
        return orderMapper.findOrderByOpenIdAndProjectId(param);
    }

    public Long insertOrder(Order order) {
        WxUser wxUser = wxUserService.getUserByOpenId(order.getOpenId());
        String orderNo = OrderNoGenerator.generateOrderNo(wxUser.getUserId());
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

    public BigDecimal computePrice(int month, String openId) {
        BigDecimal price = orderMapper.computePrice(month, openId);
        return price == null ? new BigDecimal(0) : price;
    }
}
