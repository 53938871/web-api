package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.Information;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface InformationMapper {
    List<Information> getInformations();
    Information getInformationById(Long id);
}
