package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.Project;
import cn.meilituibian.api.dto.ProjectDto;
import cn.meilituibian.api.mapper.ProjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private static final Logger logger = LogManager.getLogger(ProjectService.class);

    @Autowired
    private ProjectMapper projectMapper;

    @Cacheable(value = "meilituibian", key="'project_by_category_' + #categoryId", unless = "#result == null")
    public ProjectDto getProjectByCategoryId(Long categoryId) {
        Project project = projectMapper.getProjectByCategoryId(categoryId);
        if(project == null) {
            return null;
        }
        ProjectDto projectDto = new ProjectDto();
        String[] properties = {"projectId","categoryId","categoryName","detail","safe","concern","complex", "shortDesc", "imgPath", "content", "minPrice","maxPrice", "content"};
        String[] jsonProperties = {"operation", "treatment", "recure", "advantage","disadvantage"};
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        ObjectMapper mapper = new ObjectMapper();
        try {
            for(String property : properties) {
                propertyUtilsBean.setProperty(projectDto, property, propertyUtilsBean.getProperty(project, property));
            }
            for(String property : jsonProperties) {
                Object value = propertyUtilsBean.getProperty(project, property);
                if (value != null) {
                    propertyUtilsBean.setProperty(projectDto, property, mapper.readTree(value.toString()));
                }
            }
        } catch (Exception e) {
            logger.error("Project 转化 ProjectDto 出错;categoryId={}", categoryId);
        }
        return projectDto;
    }

    public List<Project> search(String categoryName) {
        return projectMapper.search(categoryName);
    }

    @Cacheable(value = "meilituibian", unless = "#result.size() < 1")
    public List<Project> getProjectsByCategoryIds(String[] categoryIds) {
        return projectMapper.getProjectsByCategoryIds(categoryIds);
    }

    @Cacheable(value = "meilituibian", unless = "#result.size() < 1")
    public List<Project> getProjectsBySecondCategory(String[] secondIds) {
        return projectMapper.getProjectsBySecondCategory(secondIds);
    }

    @Cacheable(value = "meilituibian", key="'project_by_id_' + #id", unless = "#result == null")
    public Project getProjectById(Long id) {
        return projectMapper.getProjectById(id);
    }

    public void updateBookNum(Long id) {
        projectMapper.updateBookNum(id);
    }

}
