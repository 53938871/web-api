package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.Advertisment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdvertismentMapper {
    List<Advertisment> getAdvList(String code);
}
