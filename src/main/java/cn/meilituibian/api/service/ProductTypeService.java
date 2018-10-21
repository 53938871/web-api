package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.ProductType;
import cn.meilituibian.api.mapper.ProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;

    public List<ProductType> list() {
        return productTypeMapper.list();
    }

    public ProductType findProductTypeById(Long id) {
        return productTypeMapper.findProductTypeById(id);
    }
}
