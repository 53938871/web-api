package cn.meilituibian.api.controller;

import cn.meilituibian.api.domain.Article;
import cn.meilituibian.api.domain.PersonalMenu;
import cn.meilituibian.api.service.PersonalRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/personal")
@Api(value="个人中心", description="个人中心")
public class PersonalCenterController {
    @Autowired
    private PersonalRoleService personalRoleService;

    @RequestMapping(path = "/menus", method = RequestMethod.GET)
    @ApiOperation(value = "根据jobTitle得到菜单")
    public ResponseEntity<?>  getPersonalMenusByJobTitle(@RequestParam(value = "jobTitle", defaultValue = "-1") int jobTitle) {
        List<PersonalMenu> list = personalRoleService.getPersonalMenusByJobTitle(jobTitle);
        return new ResponseEntity<List<PersonalMenu>>(list, HttpStatus.OK);
    }
}
