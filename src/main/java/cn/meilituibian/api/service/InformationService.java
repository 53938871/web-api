package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.Information;
import cn.meilituibian.api.mapper.InformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InformationService {
    @Autowired
    private InformationMapper informationMapper;

    public List<Information> getInformations() {
        return informationMapper.getInformations();
    }

    public List<Map<String, Object>> getInformationTitles() {
        List<Information> list = informationMapper.getInformations();
        if(!list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for(Information information: list) {
            Map<String, Object> infoMap = new HashMap<>();
            infoMap.put("id", information.getId());
            infoMap.put("title", information.getTitle());
            result.add(infoMap);
        }
        return result;
    }

    public Information getInformationById(Long id) {
        return informationMapper.getInformationById(id);
    }
}
