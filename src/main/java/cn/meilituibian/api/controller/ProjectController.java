package cn.meilituibian.api.controller;

import cn.meilituibian.api.common.ErrorResponse;
import cn.meilituibian.api.domain.Comment;
import cn.meilituibian.api.domain.Project;
import cn.meilituibian.api.dto.ProjectDto;
import cn.meilituibian.api.service.CommentService;
import cn.meilituibian.api.service.ProjectService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value="project info", description="项目详情信息")
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "根据关键字查询项目",response = List.class)
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> searchProjects(@RequestParam("q") String categoryName) {
        List<Project> list = projectService.search(categoryName);
        List<Map<String, Object>> result = processProjects(list);
        return new ResponseEntity<List<Map<String, Object>>>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "查询多个详情,传入多个三级id",response = List.class, notes = "多个id用逗号隔开")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<?> list(@RequestParam("cids") String[] cids) {
        List<Project> list = projectService.getProjectsByCategoryIds(cids);
        List<Map<String, Object>> result = processProjects(list);
        return new ResponseEntity<List<Map<String, Object>>>(result, HttpStatus.OK);
    }

    private List<Map<String, Object>> processProjects(List<Project> list) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return result;
        }
        for(Project project : list) {
            Map<String, Object> projectMap = new HashMap<>();
            projectMap.put("id", project.getProjectId());
            projectMap.put("desc", project.getShortDesc());
            projectMap.put("name", project.getCategoryName());
            projectMap.put("imgPath", project.getImgPath());
            projectMap.put("minPrice", project.getMinPrice());
            projectMap.put("maxPrice", project.getMaxPrice());
            projectMap.put("categoryId", project.getCategoryId());
            projectMap.put("categoryParentId", project.getParentCategoryId());
            projectMap.put("bookNum", project.getBookNum());
            projectMap.put("content", project.getContent());
            result.add(projectMap);
        }
        return result;
    }

    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户open_id查询评论", response = List.class)
    public ResponseEntity<?> getCommentByOpenId(@RequestParam(value = "open_id", required = true) String openId) {
        List<Comment> comments = commentService.getCommentsByOpenId(openId);
        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}/comments", method = RequestMethod.GET)
    @ApiOperation(value = "查询项目下的所有评论", response = List.class)
    public ResponseEntity<?> getCommentByOpenProjectId(@PathVariable Long projectId, @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                                       @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        Page<Comment> comments = commentService.getCommentsByProjectId(projectId, pageNo, pageSize);
        PageInfo<Comment> result = new PageInfo<>(comments);
        return new ResponseEntity<PageInfo<Comment>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}/comments", method = RequestMethod.POST)
    @ApiOperation(value = "增加评论", response = Comment.class)
    public ResponseEntity<?> insertComment(Comment comment) {
        Comment newComment = commentService.insertComment(comment);
        return new ResponseEntity<Comment>(newComment, HttpStatus.OK);
    }

    @RequestMapping(value = "/list/category", method = RequestMethod.GET)
    @ApiOperation(value = "查询某一个二级菜单下的所有三级项目详情，传入多个二级目录id",response = List.class)
    public ResponseEntity<?> getProjectsBySecondCategory(@RequestParam(value = "cids", required = true) String[] secondIds) {
        List<Project> list = projectService.getProjectsBySecondCategory(secondIds);
        List<Map<String, Object>> result = processProjects(list);
        return new ResponseEntity<List<Map<String, Object>>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询某一个项目详情",response = Project.class)
    public ResponseEntity<?> getProjectById(@PathVariable("id") Long id) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            ErrorResponse response = new ErrorResponse(200, "找不到相关的项目");
            return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
        }
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

}

