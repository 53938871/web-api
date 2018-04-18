package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectMapper {
    Project getProjectByCategoryId(Long categoryId);

    List<Project> search(String categoryName);

    List<Project> getProjectsByCategoryIds(@Param("categoryIds") String[] categoryIds);

    List<Project> getProjectsBySecondCategory(@Param("secondIds") String[] secondIds);

    Project getProjectById(Long id);

    void updateBookNum(Long id);
}
