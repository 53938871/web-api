package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.WxUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxUserMapper {
    WxUser getWxUserByOpenId(String openId);

    WxUser getWxUserById(Long id);

    Long insertWxUser(WxUser wxUser);
}
