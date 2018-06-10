package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.SalesmanGrade;
import cn.meilituibian.api.mapper.SalesManGradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesManGradeService {
    @Autowired
    private SalesManGradeMapper salesManGradeMapper;

    @Cacheable(value = "commonCache" ,unless = "#result.size() < 1")
    public List<SalesmanGrade> getGradeList() {
        List<SalesmanGrade> list = salesManGradeMapper.listGrade();
        return list;
    }

    //在这里判断业务员的业绩
    public void todo(String openId) {

    }
}
