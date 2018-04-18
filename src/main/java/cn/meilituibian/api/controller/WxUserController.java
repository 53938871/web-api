package cn.meilituibian.api.controller;

import cn.meilituibian.api.domain.WxUser;
import cn.meilituibian.api.service.WxUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value="weixin user info", description="微信用户信息")
public class WxUserController {
    @Autowired
    private WxUserService wxUserService;

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查找用户",response = WxUser.class)
    @ApiImplicitParam(name = "user_id", value = "user_id", required = true, paramType = "Long")
    public WxUser getUserById(@PathVariable Long user_id) {
        return wxUserService.getUserById(user_id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ApiOperation(value = "根据openid查找用户",response = WxUser.class)
    @ApiImplicitParam(name = "open_id", value = "open_id", required = true, paramType = "String")
    public WxUser getUserByOpenId(@RequestParam("open_id") String openId) {
        return wxUserService.getUserByOpenId(openId);
    }

    @RequestMapping(value = "/users/save", method = RequestMethod.GET)
    @ApiOperation(value = "保存用户信息",response = Long.class)
    @ApiImplicitParam(name = "WxUser", required = true, paramType = "WxUser")
    public Long insertWxUser(WxUser wxUser) {
        Long userId = wxUserService.insertWxUser(wxUser);
        return userId;
    }

    //@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
}
