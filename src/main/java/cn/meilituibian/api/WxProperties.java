package cn.meilituibian.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:wx.properties")
public class WxProperties {
    @Value("${wx.access_token.url}")
    private String accessTokenUrl;

    @Value("${wx.userinfo.url}")
    private String userInfoUrl;

    @Value("${wx.jsapi.url}")
    private String jsapiUrl;

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    public String getJsapiUrl() {
        return jsapiUrl;
    }

    public void setJsapiUrl(String jsapiUrl) {
        this.jsapiUrl = jsapiUrl;
    }
}
