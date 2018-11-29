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

    public List<Order> getOrderByGuidAndProject(String guid, int projectId) {
        Map<String, Object> param = new HashMap<>();
        param.put("guid", guid);
        param.put("projectId", projectId);
        return orderMapper.findOrderByGuidAndProjectId(param);
    }

    public Long insertOrder(Order order) {
        WxUser wxUser = wxUserService.getUserByGuid(order.getGuid());
        String orderNo = OrderNoGenerator.generateOrderNo(wxUser.getUserId());
        order.setOrderNo(orderNo);
        order.setCreateDate(new Date());
        Long id = orderMapper.insertOrder(order);
        projectMapper.updateBookNum(order.getProjectId());
        return order.getId();
    }

    public List<Order> getOrdersByGuidId(String guid){
        return orderMapper.getOrdersByGuid(guid);
    }

    public boolean updateOrder(Order order){
        order.setUpdateDate(new Date());
        int count = orderMapper.updateOrder(order);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public Order getOrderByIdAndGuid(Long id, String guid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("guid", guid);
        return orderMapper.getOrderByIdAndGuid(paramMap);
    }

    public List<Order> getClientOrderByUser(String guid) {
        return orderMapper.getClientOrderByUser(guid);
    }

    public Order getOrderById(Long id) {
        return orderMapper.getOrderById(id);
    }

    public Map<String, Object> getPerformancesByGuid(String guid) {
        Map<String, Object> result = new HashMap<>();
        List<Order> list = orderMapper.getPerformancesByGuid(guid);
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

    public BigDecimal computePrice(int month, String guid) {
        BigDecimal price = orderMapper.computePrice(month, guid);
        return price == null ? new BigDecimal(0) : price;
    }
}
