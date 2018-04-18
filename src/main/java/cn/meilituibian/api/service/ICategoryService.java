package cn.meilituibian.api.service;

import java.util.List;
import java.util.Map;

public interface ICategoryService {
    List<Map<String, Object>> listCategory();

    List<Map<String ,Object>> getFirstCategories();

    List<Map<String ,Object>> getSecondCategories(String[] parentCategoryIds);

    List<Map<String ,Object>> getThirdCategories(String[] parentCategoryIds);
}
