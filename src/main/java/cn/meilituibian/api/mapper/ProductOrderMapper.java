package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.Product;
import cn.meilituibian.api.domain.ProductOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductOrderMapper {
    ProductOrder findProductOrderByOpenId(String openId);

    int saveProductOrder(ProductOrder productOrder);

    int updateProductOrder(ProductOrder productOrder);
}
