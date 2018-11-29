package cn.meilituibian.api.service;

import cn.meilituibian.api.common.OrderNoGenerator;
import cn.meilituibian.api.domain.Product;
import cn.meilituibian.api.domain.ProductOrder;
import cn.meilituibian.api.mapper.ProductMapper;
import cn.meilituibian.api.mapper.ProductOrderMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductOrderService.class);
    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @Transactional
    public ProductOrder saveProductOrder(ProductOrder productOrder) {
        int max = 1;
        int successNum = 0;
        boolean updatedFail = true;
        int quantity = productOrder.getQuantity();
        int currentStockQuantity = 0;
        productOrder.setCreateTime(new Date());
        productOrder.setOrderNo(OrderNoGenerator.generateOrderNo(productOrder.getUserId()));

        while(updatedFail) {
            Product product= productMapper.getProductById(productOrder.getProductId());
            if (product == null) {
                LOGGER.error("扣减库存,产品不存在；id={}", productOrder.getProductId());
                throw new RuntimeException("此产品不存在");
            }
            currentStockQuantity = product.getQuantity();
            if (quantity > currentStockQuantity) { //库存不够
                LOGGER.error("扣减库存,产品Id={},库存数量={},兑换数量={}", productOrder.getProductId(), currentStockQuantity, quantity);
                throw new RuntimeException("当前库存数量不足");
            }

            Map<String, Object> param = new HashMap<>();
            param.put("id", product.getId());
            param.put("version", product.getVersion());
            param.put("quantity", quantity);

            successNum = productMapper.subtractStock(param);//扣减库存，扣减失败，重试3次
            if (successNum > 0 || max >= 3) {
                updatedFail = false;
                break;
            }
            max++;
        }

        if (!updatedFail) {
            productOrder.setCreateTime(new Date());
            productOrder.setCreateTime(new Date());
            productOrderMapper.saveProductOrder(productOrder);
        } else {
            LOGGER.error("扣减库存,产品Id={},库存数量={},兑换数量={}", productOrder.getProductId(), currentStockQuantity, quantity);
            throw new RuntimeException("当前库存数量不足,请稍候再试");
        }

        return productOrder;
    }


    public Page<ProductOrder> findProductOrderListByGuid(String guid, int pageNo, int pageSize){
        PageHelper.startPage(pageNo, pageSize);
        return productOrderMapper.findProductOrderListByGuid(guid);
    }

    public ProductOrder findProductOrderById(Long id) {
        return productOrderMapper.findOrderById(id);
    }

    public int cancelProductOrder(Map<String, Object> paramMap) {
        return productOrderMapper.cancelProductOrder(paramMap);
    }

    public int deleteProductOrder(Map<String, Object> paramMap) {
        return productOrderMapper.deleteProductOrder(paramMap);
    }
}
