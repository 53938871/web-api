package cn.meilituibian.api.controller;

import cn.meilituibian.api.domain.SalesmanGrade;
import cn.meilituibian.api.domain.WxUser;
import cn.meilituibian.api.service.SalesManGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/grade")
@Api(value="grade", description="业务等级信息")
public class SalesManGradeController {

    @Autowired
    private SalesManGradeService salesManGradeService;

    @RequestMapping("")
    @ApiOperation(value = "得到业务等级信息",response = List.class)
    public ResponseEntity<?> gradeList() {
        List<SalesmanGrade> result = salesManGradeService.getGradeList();
        return new ResponseEntity<List<SalesmanGrade>>(result, HttpStatus.OK);
    }

}
