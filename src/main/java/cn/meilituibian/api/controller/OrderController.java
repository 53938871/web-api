package cn.meilituibian.api.controller;

import cn.meilituibian.api.common.ErrorResponse;
import cn.meilituibian.api.domain.Order;
import cn.meilituibian.api.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<?> insertOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            ErrorResponse errorResponse = new ErrorResponse(400, errorMsg);
            return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        }else {
            Long id = orderService.insertOrder(order);
            Map<String, Object> result = new HashMap<>();
            result.put("id", id);
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }
    }

    @RequestMapping(value = {"/{openId}"}, method = RequestMethod.GET)
    @ApiOperation(value = "根据openId获得所有订单信息")
    public ResponseEntity<?> getOrdersByOpenId(@PathVariable String openId) {
        List<Order> list = orderService.getOrdersByOpenId(openId);
        return new ResponseEntity<List<Order>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    @ApiOperation(value = "更新订单信息")
    public ResponseEntity<?> updateOrder(@RequestBody Order order) {
        boolean success = orderService.updateOrder(order);
        if (success) {
            return new ResponseEntity<Order>(order, HttpStatus.OK);
        }
        Map<String, String> result = new HashMap<>();
        result.put("message", "更新失败");
        return new ResponseEntity<Map<String, String>>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = {"/detail/{id}"}, method = RequestMethod.GET)
    @ApiOperation(value = "根据订单id或(open id 和订单id)获得订单信息")
    public ResponseEntity<?> getOrdersByOpenId(@PathVariable Long id, @RequestParam(value = "openId", required = false) String openId) {
        Order order = null;
        if (StringUtils.isEmpty(openId)) {
            order = orderService.getOrderById(id);
        } else {
            order = orderService.getOrderByIdAndOpenId(id, openId);
        }
        if (order == null) {
            ErrorResponse response = new ErrorResponse(404, "找不到此订单");
            return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @RequestMapping(value = {"/client-orders/{openId}"}, method = RequestMethod.GET)
    @ApiOperation(value = "根据open id获得此open id的所有推荐客户的订单信息")
    public ResponseEntity<?> getClientOrdersByOpenId(@PathVariable String openId) {
        List<Order> orders = orderService.getClientOrderByUser(openId);
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }

    @RequestMapping(value = {"/performances/{openId}"}, method = RequestMethod.GET)
    @ApiOperation(value = "根据open id查询此用户的业绩")
    public ResponseEntity<?> getPerformancesByOpenId(@PathVariable String openId) {
        Map<String, Object> result = orderService.getPerformancesByOpenId(openId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    @ApiOperation(value = "根据open id,订单状态status或两者查询用户的订单,如/list?openId=1&status=0, /list?openId=1, /list?status=1")
    public ResponseEntity<?> getOrders(@RequestParam(value = "openId", required = false) String openId,
                                       @RequestParam(value = "status", required = false) Integer stauts) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("openId", openId);
        paramMap.put("status", stauts);
        List<Order> list = orderService.getOrders(paramMap);
        return new ResponseEntity<List<Order>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = {"/check"}, method = RequestMethod.GET)
    @ApiOperation(value = "下单时根据openId和projectId查看是否有没有处理过的订单")
    public ResponseEntity<?> getOrderByOpenIdAndProject(@RequestParam String openId, @RequestParam int projectId) {
        List<Order> list = orderService.getOrderByOpenIdAndProject(openId, projectId);
        return new ResponseEntity<List<Order>>(list, HttpStatus.OK);
    }
}
