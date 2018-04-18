package cn.meilituibian.api.controller;

import cn.meilituibian.api.common.ErrorResponse;
import cn.meilituibian.api.domain.Order;
import cn.meilituibian.api.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/order")
@Api(value="订单信息", description="订单信息")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ApiOperation(value = "保存订单信息")
    public ResponseEntity<?> insertOrder(Order order) {
        Long id = orderService.insertOrder(order);
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = {"/{openId}"}, method = RequestMethod.GET)
    @ApiOperation(value = "根据openId获得所有订单信息")
    public ResponseEntity<?> getOrdersByOpenId(@PathVariable String openId) {
        List<Order> list = orderService.getOrdersByOpenId(openId);
        return new ResponseEntity<List<Order>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    @ApiOperation(value = "更新订单信息")
    public ResponseEntity<?> updateOrder(Order order) {
        boolean success = orderService.updateOrder(order);
        if (success) {
            return new ResponseEntity<Order>(order, HttpStatus.OK);
        }
        Map<String, String> result = new HashMap<>();
        result.put("message", "更新失败");
        return new ResponseEntity<Map<String, String>>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = {"/id/{id}"}, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获得订单信息")
    public ResponseEntity<?> getOrdersByOpenId(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            ErrorResponse response = new ErrorResponse(404, "找不到此订单");
            return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }


}
