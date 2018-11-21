package cn.meilituibian.api.controller;

import cn.meilituibian.api.common.ErrorCode;
import cn.meilituibian.api.domain.SalesmanGrade;
import cn.meilituibian.api.domain.WxUser;
import cn.meilituibian.api.exception.ErrorResponseEntity;
import cn.meilituibian.api.service.SalesManGradeService;
import cn.meilituibian.api.service.WxUserService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/users", produces ={ MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
@Api(value="weixin user info", description="微信用户信息")
public class WxUserController {
    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private SalesManGradeService salesManGradeService;

    @RequestMapping(value = "/id/{guid}", method = RequestMethod.GET)
    @ApiOperation(value = "根据guid查找用户",response = WxUser.class)
    public ResponseEntity<?> getUserById(@PathVariable("guid") String guid) {
        WxUser wxUser = wxUserService.getUserById(guid);
        return new ResponseEntity<WxUser>(wxUser, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "根据openid查找用户,分享后进入此接口",response = WxUser.class)
    public ResponseEntity<?> getUserByOpenId(@RequestParam(value = "open_id", required = true) String openId,
                                             @RequestParam(value = "parent", required = false) String parent,
                                             @RequestParam(value = "nickName", required = false) String nickName) {
        WxUser wxUser = wxUserService.getUserByOpenId(openId, parent, nickName);
        return new ResponseEntity<WxUser>(wxUser, HttpStatus.OK);
    }

    @ApiOperation(value = "保存用户信息 userType(0:普通用户,1:商家,2:业务员)")
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public ResponseEntity<?> insertWxUser(@RequestBody WxUser wxUser) {
        WxUser tempUser = wxUserService.findWxUserByPhone(wxUser.getPhone());
        if (tempUser != null) {
            ErrorResponseEntity entity = ErrorResponseEntity.fail(wxUser, ErrorCode.PHONE_IS_EXISTS.getCode(), ErrorCode.PHONE_IS_EXISTS.getMessage());
            return new ResponseEntity<ErrorResponseEntity>(entity, HttpStatus.BAD_REQUEST);
        }
        WxUser currentUser = wxUserService.insertWxUser(wxUser);
        return new ResponseEntity<WxUser>(currentUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "保存用户信息",response = Long.class)
    public ResponseEntity<?> updateWxUser(@RequestBody WxUser wxUser) {
        wxUserService.updateWxUser(wxUser);
        WxUser user = wxUserService.getUserById(wxUser.getGuid());
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
    @ApiOperation(value = "用户登录，根据用户名userName和密码password登录")
    public ResponseEntity<?> login(@RequestBody WxUser wxUser) {
        String userName = wxUser.getUserName();
        String password = wxUser.getPassword();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            ErrorResponseEntity errorResponseEntity = ErrorResponseEntity.fail(wxUser, HttpStatus.OK.value(), "用户名和密码不能为空");
            return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, HttpStatus.OK);
        }
        WxUser user = wxUserService.login(userName, password);
        if (user == null) {
            ErrorResponseEntity errorResponseEntity = ErrorResponseEntity.fail(wxUser, HttpStatus.OK.value(), "错误的用户名或密码");
            return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, HttpStatus.OK);
        }
        return new ResponseEntity<WxUser>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/wx", method = RequestMethod.GET)
    @ApiOperation(value = "获取微信用户信息")
    public ResponseEntity<?> getWxInfo(@RequestParam("code") String code, @RequestParam String appid,
                                       @RequestParam("secret") String secret) {
        JSONObject result = wxUserService.getWxUserInfo(appid, secret, code);
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/wx/signature", method = RequestMethod.GET)
    @ApiOperation(value = "获取微信签名，nonceStr和timestamp可以不传")
    public ResponseEntity<?> getSignature(@RequestParam String url,
                                          @RequestParam(value = "nonceStr", required = false) String nonceStr,
                                          @RequestParam(value = "timestamp", required = false) String timestamp) {
        JSONObject tokenJson = wxUserService.getAccessToken();
        if (tokenJson == null ) {
            ErrorResponseEntity entity = ErrorResponseEntity.fail(null, HttpStatus.BAD_REQUEST.value(), "获取AccessToken失败");
            return new ResponseEntity<ErrorResponseEntity>(entity, HttpStatus.OK);
        }
        String accessToken = tokenJson.getString("access_token");
        JSONObject result = wxUserService.getSignature(accessToken, url, nonceStr, timestamp);
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/wx/accessToken", method = RequestMethod.GET)
    @ApiOperation(value = "获取信息")
    public ResponseEntity<?> getAccessToken(@RequestParam("code") String code, @RequestParam String appid,
                                       @RequestParam("secret") String secret) {
        JSONObject result = wxUserService.getAccessToken(appid, secret, code);
        if (result.containsKey("errcode") && !result.containsKey("access_token")) {
            ErrorResponseEntity entity = ErrorResponseEntity.fail(result, HttpStatus.BAD_REQUEST.value(), "获取AccessToken失败");
            return new ResponseEntity<ErrorResponseEntity>(entity, HttpStatus.OK);
        }
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }


    @RequestMapping(value = "/upgrade/{openId}", method = RequestMethod.POST)
    @ApiOperation(value = "用户升级")
    public ResponseEntity<?> upgrade(@PathVariable("openId") String openId, @RequestBody(required = false) WxUser wxUser) {
        wxUser.setOpenId(openId);
        wxUserService.upgrade(wxUser);
        WxUser user = wxUserService.getUserByOpenId(openId);
        return new ResponseEntity<WxUser>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/upgrade/{openId}/info", method = RequestMethod.GET)
    @ApiOperation(value = "得到升级信息")
    public ResponseEntity<?> upgradeInfo(@PathVariable String openId) {
        Map<String,Object> result = salesManGradeService.upgrade(openId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

}
