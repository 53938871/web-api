package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.PersonalRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PersonalRoleMapper {
    PersonalRole getPersonalRoleByJobTitle(@Param("jobTitle") int jobTitle);
}
