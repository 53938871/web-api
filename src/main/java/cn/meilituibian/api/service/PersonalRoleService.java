package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.PersonalMenu;
import cn.meilituibian.api.domain.PersonalRole;
import cn.meilituibian.api.mapper.PersonalMenuMapper;
import cn.meilituibian.api.mapper.PersonalRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalRoleService {
    private static final int DEFAULT_USER = -1;

    @Autowired
    private PersonalMenuMapper personalMenuMapper;

    @Autowired
    private PersonalRoleMapper personalRoleMapper;

    @Cacheable(value = "meilituibian" ,unless = "#result.size() < 1")
    public List<PersonalMenu> getPersonalMenusByJobTitle(int jobTitle) {
        PersonalRole personalRole = personalRoleMapper.getPersonalRoleByJobTitle(jobTitle);
        if (personalRole == null) {
            personalRole = personalRoleMapper.getPersonalRoleByJobTitle(DEFAULT_USER);
        }
        if (personalRole == null) {
            return Collections.emptyList();
        }
        String menus = personalRole.getMenus();
        String[] menuArray = menus.split(",");
        List<String> ids = Arrays.asList(menuArray);
        List<Integer> menuIds = ids.stream().map(s->{return Integer.parseInt(s.trim());}).collect(Collectors.toList());
        return personalMenuMapper.getPersonalMenusByIds(menuIds);
    }
}
