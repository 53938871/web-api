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
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.meilituibian.api.common.Constants.USER_TYPE_MERCHAT;

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
    public WxUser insertWxUser(WxUser wxUser) {
        String password = null;
        if (wxUser.getUserType() == USER_TYPE_MERCHAT) { //商家
            password = wxUser.getPhone().substring(wxUser.getPhone().length() - 6);
            wxUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        }
        Long id = wxUserMapper.insertWxUser(wxUser);
        wxUser.setPassword(password);
        return wxUser;
    }

    public List<WxUser> selectChildUser(String openId){
        return wxUserMapper.selectChildUser(openId);
    }

    @Transactional
    public void updateWxUser(WxUser wxUser) {
        if (StringUtils.isEmpty(wxUser.getPassword())) {
            String md5Password = DigestUtils.md5DigestAsHex(wxUser.getPassword().getBytes());
            wxUser.setPassword(md5Password);
        }
        wxUserMapper.updateWxUser(wxUser);
    }

    public WxUser login(String phone, String password) {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", md5Password);
        WxUser wxUser = wxUserMapper.login(map);
        return wxUser;
    }

    public WxUser findWxUserByPhone(String phone) {
        return wxUserMapper.findWxUserByPhone(phone);
    }
}
