package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.ProductOrder;
import cn.meilituibian.api.mapper.ProductOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductOrderService {
    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Autowired
    private ProductService productService;

    public synchronized List<String> saveProductOrder(ProductOrder productOrder) {
        List<String> errorList = new ArrayList<>();
        /*boolean hasStock = productService.hasStock(productOrder.getId());
        if (!hasStock) {
            errorList.add("库存不足");
            return errorList;
        }*/
        productOrder.setCreateTime(new Date());
        productOrderMapper.saveProductOrder(productOrder);
        return null;
    }
}
