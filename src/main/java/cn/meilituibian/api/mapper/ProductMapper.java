package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.Product;
import cn.meilituibian.api.domain.ProductOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ProductMapper {
    Page<Product> list();

    int getQuantityById(Long id);

    Product getProductById(Long id);

    int subtractStock(Map<String, Object> param);
}
