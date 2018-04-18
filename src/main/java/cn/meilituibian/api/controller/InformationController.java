package cn.meilituibian.api.controller;

import cn.meilituibian.api.domain.Information;
import cn.meilituibian.api.service.InformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Map<String, Object>> getInformationTitles() {
        return informationService.getInformationTitles();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "得到菜单所有基本信息",response = List.class)
    public List<Information> getInformations() {
        return informationService.getInformations();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "得到菜单所有基本信息",response = Information.class)
    public Information getInformationById(@PathVariable Long id) {
        return informationService.getInformationById(id);
    }
}
