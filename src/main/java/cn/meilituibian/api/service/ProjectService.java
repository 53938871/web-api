package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.Project;
import cn.meilituibian.api.dto.ProjectDto;
import cn.meilituibian.api.mapper.ProjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.ibatis.reflection.ArrayUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {
    private static final Logger logger = LogManager.getLogger(ProjectService.class);
    @Autowired
    private ProjectMapper projectMapper;

    public ProjectDto getProjectByCategoryId(Long categoryId) {
        Project project = projectMapper.getProjectByCategoryId(categoryId);
        if(project == null) {
            return null;
        }
        ProjectDto projectDto = new ProjectDto();
        String[] properties = {"projectId","categoryId","categoryName","detail","safe","concern","complex", "shortDesc", "imgPath"};
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
            logger.error("Project 转化 ProjectDto 出错");
        }
        return projectDto;
    }

    public List<Project> search(String categoryName) {
        return projectMapper.search(categoryName);
    }

    public List<Project> getProjectsByCategoryIds(String[] categoryIds) {
        return projectMapper.getProjectsByCategoryIds(categoryIds);
    }

    public List<Project> getProjectsBySecondCategory(String[] secondIds) {
        return projectMapper.getProjectsBySecondCategory(secondIds);
    }

    public Project getProjectById(Long id) {
        return projectMapper.getProjectById(id);
    }

    public void updateBookNum(Long id) {
        projectMapper.updateBookNum(id);
    }

}
