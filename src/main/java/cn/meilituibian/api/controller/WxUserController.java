package cn.meilituibian.api.controller;

import cn.meilituibian.api.domain.WxUser;
import cn.meilituibian.api.exception.ErrorResponseEntity;
import cn.meilituibian.api.exception.ExceptionResponse;
import cn.meilituibian.api.service.WxUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
    @ApiOperation(value = "保存用户信息 userType(0:普通用户,1:商家)",response = Long.class)
    public ResponseEntity<?> insertWxUser(@RequestBody WxUser wxUser) {
        if (StringUtils.isEmpty(wxUser.getPhone())) {
            ErrorResponseEntity entity = ErrorResponseEntity.fail(wxUser, HttpStatus.BAD_REQUEST.value(), "手机号码不能为空");
            return new ResponseEntity<ErrorResponseEntity>(entity, HttpStatus.BAD_REQUEST);
        }
        WxUser tempUser = wxUserService.findWxUserByPhone(wxUser.getPhone());
        if (tempUser != null) {
            ErrorResponseEntity entity = ErrorResponseEntity.fail(wxUser, HttpStatus.BAD_REQUEST.value(), "此手机号码已存在");
            return new ResponseEntity<ErrorResponseEntity>(entity, HttpStatus.BAD_REQUEST);
        }
        WxUser currentUser = wxUserService.insertWxUser(wxUser);
        return new ResponseEntity<WxUser>(currentUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "保存用户信息",response = Long.class)
    public ResponseEntity<?> updateWxUser(@RequestBody WxUser wxUser) {
        wxUserService.updateWxUser(wxUser);
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录，根据手机号phone和密码password登录")
    public ResponseEntity<?> login(@RequestBody WxUser wxUser) {
        String phone = wxUser.getPhone();
        String password = wxUser.getPassword();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            ErrorResponseEntity errorResponseEntity = ErrorResponseEntity.fail(null, HttpStatus.BAD_REQUEST.value(), "手机和密码不能为空");
            return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, HttpStatus.BAD_REQUEST);
        }
        WxUser user = wxUserService.login(phone, password);
        if (user == null) {
            ErrorResponseEntity errorResponseEntity = ErrorResponseEntity.fail(null, HttpStatus.NOT_FOUND.value(), "错误的手机号或密码");
            return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<WxUser>(wxUser, HttpStatus.OK);
    }
}
