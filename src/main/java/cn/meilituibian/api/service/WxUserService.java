package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.WxUser;
import cn.meilituibian.api.exception.ApiException;
import cn.meilituibian.api.mapper.WxUserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WxUserService {
    private static final Logger logger = LogManager.getLogger(WxUserService.class);
    @Autowired
    private WxUserMapper wxUserMapper;

    public WxUser getUserById(Long user_id) {
        WxUser user = wxUserMapper.getWxUserById(user_id);
        if(user == null) {
            logger.warn("user is not found; user_id={}", user_id);
            throw new ApiException(HttpStatus.NOT_FOUND, "user is not fouod");
        }
        return user;
    }

    public WxUser getUserByOpenId(String openId) {
        WxUser wxUser = wxUserMapper.getWxUserByOpenId(openId);
        if(wxUser == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "user is not fouod");
        }
        return wxUser;
    }

    @Transactional
    public Long insertWxUser(WxUser wxUser) {
        return wxUserMapper.insertWxUser(wxUser);
    }

    public List<WxUser> selectChildUser(String openId){
        return wxUserMapper.selectChildUser(openId);
    }

    @Transactional
    public void updateWxUser(WxUser wxUser) {
        wxUserMapper.updateWxUser(wxUser);
    }
}
