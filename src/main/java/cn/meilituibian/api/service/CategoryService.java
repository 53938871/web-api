package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.Category;
import cn.meilituibian.api.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);
    @Autowired
    private CategoryMapper categoryMapper;

    @Cacheable(value = "meilituibian" ,unless = "#result.size() < 1")
    public List<Map<String, Object>> listCategory() {
        List<Category> categories = categoryMapper.listCategory();
        List<Category> firstCategoryList = new ArrayList<>();
        List<Category> secondCategoryList = new ArrayList<>();
        List<Category> thirdCategoryList = new ArrayList<>();
        for(Category category: categories) {
            if(category.getParentId() == null) {
                firstCategoryList.add(category);
                continue;
            }
            if(category.getGrade() !=null && category.getGrade() == 2) {
                secondCategoryList.add(category);
            }else {
                thirdCategoryList.add(category);
            }
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for(Category category: firstCategoryList) {
            Map<String, Object> categoryMap = setCategoryToMap(category);
            List<Map<String, Object>> secondList = new ArrayList<>();
            for(Category secondCategory: secondCategoryList) {
                List<Map<String, Object>> thirdList = new ArrayList<>();
                if(secondCategory.getParentId() == category.getId()) {
                    Map<String, Object> secondCategoryMap = setCategoryToMap(secondCategory);
                    secondList.add(secondCategoryMap);

                    thirdCategoryList.stream().filter(s->secondCategory.getId() == s.getParentId())
                            .forEach(s->thirdList.add(setCategoryToMap(s)));
                    secondCategoryMap.put("third", thirdList);
                }
            }
            categoryMap.put("second", secondList);
            result.add(categoryMap);
        }
        return result;
    }

    private Map<String, Object> setCategoryToMap(Category category) {
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("id", category.getId());
        categoryMap.put("name", category.getName());
        categoryMap.put("hot", category.isHot());
        categoryMap.put("icon", category.getIcon());
        categoryMap.put("parentId", category.getParentId());
        return categoryMap;
    }

    @Cacheable(value = "meilituibian", unless = "#result.size() < 1")
    public List<Map<String ,Object>> getFirstCategories() {
        return getCategories(1L, null);
    }

    private List<Map<String ,Object>> getCategories(Long grade, String[] parentCategoryIds) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("grade", grade);
        paramMap.put("parentCategoryIds", parentCategoryIds);
        List<Category> list = categoryMapper.getChildCategories(paramMap);
        for (Category category: list) {
            result.add(setCategoryToMap(category));
        }
        return result;
    }

    @Cacheable(value = "meilituibian", unless = "#result.size() < 1")
    public List<Map<String ,Object>> getSecondCategories(String[] parentCategoryIds) {
        return getCategories(2L, parentCategoryIds);
    }


    @Cacheable(value = "meilituibian", unless = "#result.size() < 1")
    public List<Map<String ,Object>> getThirdCategories(String[] parentCategoryIds) {
        return getCategories(3L, parentCategoryIds);
    }

}
