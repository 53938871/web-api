package cn.meilituibian.api.controller;

import cn.meilituibian.api.domain.Category;
import cn.meilituibian.api.dto.ProjectDto;
import cn.meilituibian.api.service.CategoryService;
import cn.meilituibian.api.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value="operate category info", description="菜单信息")
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有菜单项",response = List.class)
    public ResponseEntity<List<Map<String, Object>>> categoryList() {
        return new ResponseEntity(categoryService.listCategory(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{categoryId}/projects", method = RequestMethod.GET)
    @ApiOperation(value = "查询某一个三级菜单项目详情，传入一个三级菜单id",response = List.class)
    public ResponseEntity<?> getProjectByCategoryId(@PathVariable Long categoryId) {
        List<ProjectDto> list = new ArrayList<>();
        ProjectDto projectDto = projectService.getProjectByCategoryId(categoryId);
        if(projectDto != null) {
            list.add(projectDto);
        }
        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/first", method = RequestMethod.GET)
    @ApiOperation(value = "查询一级菜单",response = List.class)
    public ResponseEntity<?> getFirstCategories() {
        List<Map<String, Object>> list = categoryService.getFirstCategories();
        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/second", method = RequestMethod.GET)
    @ApiOperation(value = "根据一级菜单查询二级菜单",response = List.class)
    public ResponseEntity<?> getSecondCategories(@RequestParam("ids") String[]  ids) {
        List<Map<String, Object>> list = categoryService.getSecondCategories(ids);
        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/third", method = RequestMethod.GET)
    @ApiOperation(value = "根据二级菜单查询所有三级菜单，可以传个多个二级菜单，以逗号隔开",response = List.class)
    public ResponseEntity<?> getThirdCategories(@RequestParam("ids") String[]  ids) {
        List<Map<String, Object>> list = categoryService.getThirdCategories(ids);
        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }

}
