package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.ProductOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ProductOrderMapper {
    Page<ProductOrder> findProductOrderListByUserId(long userId);

    ProductOrder findOrderById(Long id);

    int saveProductOrder(ProductOrder productOrder);

    int cancelProductOrder(Map<String, Object> paramMap);

    int deleteProductOrder(Map<String, Object> paramMap);
}
