package cn.meilituibian.api.controller;

import cn.meilituibian.api.domain.ShareInfo;
import cn.meilituibian.api.service.ShareInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/share")
@Api(value="share ", description="分享信息")
public class ShareInfoController {
    @Autowired
    private ShareInfoService shareInfoService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存分享信息")
    public ResponseEntity<?> save(@RequestBody ShareInfo shareInfo) {
        shareInfoService.saveShareInfo(shareInfo);
        return new ResponseEntity<ShareInfo>(shareInfo, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "列出此用户的所有分享信息")
    public ResponseEntity<?> list(@RequestParam("userId") Long userId) {
        List<ShareInfo> infos = shareInfoService.list(userId);
        return new ResponseEntity<List<ShareInfo>>(infos, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查看分享信息")
    public ResponseEntity<?> findShareInfoById(@PathVariable("id") Long id) {
        ShareInfo info = shareInfoService.findShareInfoById(id);
        return new ResponseEntity<ShareInfo>(info, HttpStatus.OK);

    }

}
