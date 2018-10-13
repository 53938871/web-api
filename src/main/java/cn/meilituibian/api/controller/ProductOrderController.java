package cn.meilituibian.api.controller;

import cn.meilituibian.api.domain.Product;
import cn.meilituibian.api.domain.ProductOrder;
import cn.meilituibian.api.domain.WxUser;
import cn.meilituibian.api.service.ProductOrderService;
import cn.meilituibian.api.service.WxUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
}
