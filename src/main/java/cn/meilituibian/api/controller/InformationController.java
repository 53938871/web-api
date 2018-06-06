package cn.meilituibian.api.controller;

import cn.meilituibian.api.domain.Information;
import cn.meilituibian.api.service.InformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api(value="基本信息", description="基本信息")
@RequestMapping("/infos")
public class InformationController {
    @Autowired
    private InformationService informationService;

    @RequestMapping(value = {"/titles"}, method = RequestMethod.GET)
    @ApiOperation(value = "得到基本信息菜单名称和ID",response = Map.class)
    public ResponseEntity<?> getInformationTitles() {
        List<Map<String, Object>> list = informationService.getInformationTitles();
        return new ResponseEntity<List<Map<String, Object>>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "得到菜单所有基本信息",response = List.class)
    public ResponseEntity<?>  getInformations() {
        List<Information> list = informationService.getInformations();
        return new ResponseEntity<List<Information>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "得到菜单所有基本信息",response = Information.class)
    public ResponseEntity<?> getInformationById(@PathVariable Long id) {
        Information information = informationService.getInformationById(id);
        return new ResponseEntity<Information>(information, HttpStatus.OK);
    }
}
