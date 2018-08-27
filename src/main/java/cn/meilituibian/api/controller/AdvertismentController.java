package cn.meilituibian.api.controller;

import RequestForm.AdvertismentRequestForm;
import cn.meilituibian.api.common.ApiResultStatus;
import cn.meilituibian.api.domain.Advertisment;
import cn.meilituibian.api.service.AdvertismentService;
import io.swagger.annotations.Api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(value="广告查询")
@RequestMapping(value="/advertisment")
public class AdvertismentController {
    private static final Logger LOGGER = LogManager.getLogger(AdvertismentController.class);

    @Autowired
    private AdvertismentService advertismentService;

    @RequestMapping(value="/get_adv_list", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult getAdvList(@RequestBody AdvertismentRequestForm advertismentRequestForm){
        ApiResult result = new ApiResult();
        try{
            List<Advertisment> lsit = this.advertismentService.getAdvertismentList(advertismentRequestForm.getCode());
            result.setResult(lsit);
            result.setCode(ApiResultStatus.SUCCESS);
        }catch (Exception ex){
            result.setMsg(ex.getMessage());
            result.setCode(ApiResultStatus.ERROR);
        }

        return result;
    }

}
