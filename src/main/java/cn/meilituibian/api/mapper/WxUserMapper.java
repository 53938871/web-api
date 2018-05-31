package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WxUserMapper {
    WxUser getWxUserByOpenId(String openId);

    WxUser getWxUserById(Long id);

    Long insertWxUser(WxUser wxUser);

    List<WxUser> selectChildUser(String openId);

    void updateWxUser(WxUser wxUser);

    //登录
    WxUser login(Map<String, String> map);

    WxUser findWxUserByPhone(String phone);

    Long getWxUserIdByOpenId(String openId);

}
