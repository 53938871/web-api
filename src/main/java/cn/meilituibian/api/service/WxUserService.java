package cn.meilituibian.api.service;

import cn.meilituibian.api.WxProperties;
import cn.meilituibian.api.domain.WxUser;
import cn.meilituibian.api.exception.ApiException;
import cn.meilituibian.api.mapper.WxUserMapper;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

import static cn.meilituibian.api.common.Constants.USER_TYPE_MERCHAT;

@Service
public class WxUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxUserService.class);
    @Autowired
    private WxUserMapper wxUserMapper;

    @Autowired
    private WxProperties wxProperties;

    @Autowired
    private RestTemplate restTemplate;

    public WxUser getUserById(Long user_id) {
        WxUser user = wxUserMapper.getWxUserById(user_id);
        if(user == null) {
            LOGGER.warn("user is not found; user_id={}", user_id);
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

    public WxUser getWxUserIdByOpenId(String openId) {
        return wxUserMapper.getWxUserIdByOpenId(openId);
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

    public WxUser login(String userName, String password) {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("password", md5Password);
        WxUser wxUser = wxUserMapper.login(map);
        return wxUser;
    }

    public WxUser findWxUserByPhone(String phone) {
        return wxUserMapper.findWxUserByPhone(phone);
    }


    public JSONObject getAccessToken(String appid, String secret, String code) {
        String tokenUrl = wxProperties.getAccessTokenUrl();
        tokenUrl = String.format(tokenUrl, appid, secret, code);
        String tokenContent = restTemplate.getForObject(tokenUrl,String.class);
        JSONObject json = JSONObject.parseObject(tokenContent);
        return json;
    }

    public JSONObject getWxUserInfo(String appid, String secret, String code) {
        String tokenUrl = wxProperties.getAccessTokenUrl();
        tokenUrl = String.format(tokenUrl, appid, secret, code);
        String tokenContent = restTemplate.getForObject(tokenUrl,String.class);
        JSONObject json = JSONObject.parseObject(tokenContent);
        if (!json.containsKey("access_token")) {
            return json;
        }
        String accessToken=json.getString("access_token");
        String openId=json.getString("openid");
        String userInfoUrl = wxProperties.getUserInfoUrl();
        userInfoUrl = String.format(userInfoUrl, accessToken, openId);
        String userInfo = restTemplate.getForObject(userInfoUrl,String.class);
        JSONObject userInfoJson = JSONObject.parseObject(userInfo);
        userInfoJson.put("access_token", accessToken);
        return userInfoJson;
    }

    private JSONObject getJsapiTicket(String accessToken) {
        String jsapiUrl = wxProperties.getJsapiUrl();
        jsapiUrl = String.format(jsapiUrl, accessToken);
        String jsapiTicket = restTemplate.getForObject(jsapiUrl, String.class);
        JSONObject jsapiTicketJson = JSONObject.parseObject(jsapiTicket);
        LOGGER.info("ticket={}", jsapiTicketJson.toJSONString());
        return jsapiTicketJson;
    }

    public JSONObject getSignature(String accessToken, String url, String nonceStr, String timestamp) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject jsapiTicketJson = getJsapiTicket(accessToken);
            String ticket = jsapiTicketJson.getString("ticket");
            SortedMap<String,String> parameters = new TreeMap<>();
            String tempNonceStr = StringUtils.isEmpty(nonceStr) ? create_nonce_str() : nonceStr;
            String tempTimestamp = StringUtils.isEmpty(timestamp) ? create_timestamp() : timestamp;
            parameters.put("jsapi_ticket", ticket);
            parameters.put("noncestr", tempNonceStr);
            parameters.put("timestamp", tempTimestamp);
            parameters.put("url", URLDecoder.decode(url, "utf-8"));


            jsonObject.put("noncestr", tempNonceStr);
            jsonObject.put("timestamp", tempTimestamp);
            jsonObject.put("ticket", ticket);
            jsonObject.put("access_token", accessToken);

            StringBuilder query = new StringBuilder();
            parameters.forEach((k,v)->{
                query.append("&");
                query.append(k);
                query.append("=");
                query.append(v);
            });


            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(query.substring(1).toString().getBytes("UTF-8"));
            String signature = byteToHex(crypt.digest());
            jsonObject.put("signature", signature);
            LOGGER.info("参数为={},签名参数={},签名为={}",jsonObject.toJSONString(), query.substring(1).toString(), signature);
        } catch (Exception e) {
            LOGGER.error("签名失败", e);
        }
        return jsonObject;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
