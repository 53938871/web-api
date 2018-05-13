package cn.meilituibian.api.controller;

import cn.meilituibian.api.domain.WxUser;
import cn.meilituibian.api.service.WxUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(value="weixin user info", description="微信用户信息")
public class WxUserController {
    @Autowired
    private WxUserService wxUserService;

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查找用户",response = WxUser.class)
    public WxUser getUserById(@PathVariable("id") Long user_id) {
        return wxUserService.getUserById(user_id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "根据openid查找用户",response = WxUser.class)
    public WxUser getUserByOpenId(@RequestParam(value = "open_id", required = true) String openId) {
        return wxUserService.getUserByOpenId(openId);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存用户信息",response = Long.class)
    public Long insertWxUser(@RequestBody WxUser wxUser) {
        Long userId = wxUserService.insertWxUser(wxUser);
        return userId;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "保存用户信息",response = Long.class)
    public ResponseEntity<?> updateWxUser(@RequestBody WxUser wxUser) {
        Long userId = wxUserService.insertWxUser(wxUser);
        WxUser user = wxUserService.getUserById(wxUser.getUserId());
        return new ResponseEntity<WxUser>(user, HttpStatus.OK);
    }

    //@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)

    @RequestMapping(value = "/children", method = RequestMethod.GET)
    @ApiOperation(value = "根据自己的openId查询下级人员信息")
    public ResponseEntity<?> selectChildrenUser(@RequestParam(value = "openId", required = true) String openId) {
        List<WxUser> list = wxUserService.selectChildUser(openId);
        return new ResponseEntity<List<WxUser>>(list, HttpStatus.OK);
    }
}
