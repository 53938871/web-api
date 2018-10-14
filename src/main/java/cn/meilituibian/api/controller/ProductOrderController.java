package cn.meilituibian.api.controller;

import cn.meilituibian.api.common.Constants;
import cn.meilituibian.api.domain.Product;
import cn.meilituibian.api.domain.ProductOrder;
import cn.meilituibian.api.domain.WxUser;
import cn.meilituibian.api.exception.ErrorResponseEntity;
import cn.meilituibian.api.service.ProductOrderService;
import cn.meilituibian.api.service.WxUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@Api(value="product order", description="产品订单")
@RequestMapping("/product_order")
public class ProductOrderController {

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private ProductOrderService productOrderService;

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ApiOperation(value = "兑换产品")
    public ResponseEntity<?> saveProductOrder(@RequestParam(value = "openId", required = true) String openId,
                                              @RequestParam(value = "productId", required = true) Long productId,
                                              @RequestParam(value = "quantity", required = true) int quantity,
                                              @RequestParam(value = "address", required = true) String address,
                                              @RequestParam(value = "phone", required = true) String phone, @RequestParam(required = false) String remark) {
        WxUser wxUser = wxUserService.getUserByOpenId(openId);
        ProductOrder productOrder = new ProductOrder(productId, quantity, address, phone, remark);
        productOrder.setUserId(wxUser.getUserId());
        productOrder.setOpenId(openId);
        productOrderService.saveProductOrder(productOrder);
        return new ResponseEntity<ProductOrder>(productOrder, HttpStatus.OK);
    }

    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查看单个产品订单明细")
    public ResponseEntity<?> findProductOrderById(@PathVariable("id") long id) {
        ProductOrder order = productOrderService.findProductOrderById(id);
        return new ResponseEntity<ProductOrder>(order, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查看此用户所有产品订单信息")
    public ResponseEntity<?> findProductOrderListByUserId(@RequestParam("userId")long  userId, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {
        Page<ProductOrder> orders = productOrderService.findProductOrderListByUserId(userId, pageNo, Constants.PAGE_SIZE);
        PageInfo<ProductOrder> result = new PageInfo<>(orders);
        return new ResponseEntity<PageInfo<ProductOrder>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "取消未处理的订单")
    public ResponseEntity<?> cancelProductOrder(@RequestParam("userId")long  userId, @RequestParam("id")long id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("userId", userId);
        int successNum = productOrderService.cancelProductOrder(paramMap);

        if (successNum < 1) {
            return new ResponseEntity<ErrorResponseEntity>(ErrorResponseEntity.fail(successNum,500, "取消失败"), HttpStatus.OK);
        }
        return new ResponseEntity<Map<String, Object>>(paramMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "删除已取消或已完成的订单")
    public ResponseEntity<?> deleteProductOrder(@RequestParam("userId")long  userId, @RequestParam("id")long id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("userId", userId);
        int successNum = productOrderService.deleteProductOrder(paramMap);
        if (successNum < 1) {
            return new ResponseEntity<ErrorResponseEntity>(ErrorResponseEntity.fail(successNum,500, "删除失败"), HttpStatus.OK);
        }

        return new ResponseEntity<Map<String, Object>>(paramMap, HttpStatus.OK);
    }
}

