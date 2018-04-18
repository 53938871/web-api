package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    List<Category> listCategory();

    Category findCategoryById(Long category_id);

    List<Category> getFirstCategories();

    List<Category> getChildCategories(Map<String, Object> params);
}
