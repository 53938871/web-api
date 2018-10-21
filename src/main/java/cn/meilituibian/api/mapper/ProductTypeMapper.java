package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.ProductType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductTypeMapper {
    List<ProductType> list();

    ProductType findProductTypeById(@Param("id")Long id);
}
