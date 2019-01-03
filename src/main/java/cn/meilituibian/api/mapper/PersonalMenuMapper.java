package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.PersonalMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PersonalMenuMapper {
    List<PersonalMenu> getPersonalMenusByIds(@Param("ids") List<Integer> ids);
}
