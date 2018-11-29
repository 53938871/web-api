package cn.meilituibian.api.controller;

import cn.meilituibian.api.domain.Order;
import cn.meilituibian.api.exception.ErrorResponseEntity;
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
            ErrorResponseEntity errorResponse = ErrorResponseEntity.fail(order,400, errorMsg);
            return new ResponseEntity<ErrorResponseEntity>(errorResponse, HttpStatus.OK);
        }else {
            Long id = orderService.insertOrder(order);
            return new ResponseEntity<Order>(order, HttpStatus.OK);
        }
    }

    @RequestMapping(value = {"/{guid}"}, method = RequestMethod.GET)
    @ApiOperation(value = "根据guid获得所有订单信息")
    public ResponseEntity<?> getOrdersByGuid(@PathVariable String guid) {
        List<Order> list = orderService.getOrdersByGuidId(guid);
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
        ErrorResponseEntity errorResponse = ErrorResponseEntity.fail(order,500, "更新失败");
        return new ResponseEntity<ErrorResponseEntity>(errorResponse, HttpStatus.OK);
    }

    @RequestMapping(value = {"/detail/{id}"}, method = RequestMethod.GET)
    @ApiOperation(value = "根据订单id或(open id 和订单id)获得订单信息")
    public ResponseEntity<?> getOrdersByGuid(@PathVariable Long id, @RequestParam(value = "guid", required = false) String guid) {
        Order order = null;
        if (StringUtils.isEmpty(guid)) {
            order = orderService.getOrderById(id);
        } else {
            order = orderService.getOrderByIdAndGuid(id, guid);
        }
        if (order == null) {
            ErrorResponseEntity response = ErrorResponseEntity.fail(order, 404, "找不到此订单");
            return new ResponseEntity<ErrorResponseEntity>(response, HttpStatus.OK);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @RequestMapping(value = {"/client-orders/{guid}"}, method = RequestMethod.GET)
    @ApiOperation(value = "根据open id获得此open id的所有推荐客户的订单信息")
    public ResponseEntity<?> getClientOrdersByGuid(@PathVariable String guid) {
        List<Order> orders = orderService.getClientOrderByUser(guid);
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }

    @RequestMapping(value = {"/performances/{guid}"}, method = RequestMethod.GET)
    @ApiOperation(value = "根据guid查询此用户的业绩")
    public ResponseEntity<?> getPerformancesByGuid(@PathVariable String guid) {
        Map<String, Object> result = orderService.getPerformancesByGuid(guid);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    @ApiOperation(value = "根据guid,订单状态status或两者查询用户的订单,如/list?guid=1&status=0, /list?guid=1, /list?status=1")
    public ResponseEntity<?> getOrders(@RequestParam(value = "guid", required = false) String guid,
                                       @RequestParam(value = "status", required = false) Integer stauts) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("guid", guid);
        paramMap.put("status", stauts);
        List<Order> list = orderService.getOrders(paramMap);
        return new ResponseEntity<List<Order>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = {"/check"}, method = RequestMethod.GET)
    @ApiOperation(value = "下单时根据guid和projectId查看是否有没有处理过的订单")
    public ResponseEntity<?> getOrderByOpenIdAndProject(@RequestParam String guid, @RequestParam int projectId) {
        List<Order> list = orderService.getOrderByGuidAndProject(guid, projectId);
        return new ResponseEntity<List<Order>>(list, HttpStatus.OK);
    }
}
