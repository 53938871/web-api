package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.Product;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    Page<Product> list();
}
