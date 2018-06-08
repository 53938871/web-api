package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.SalesmanGrade;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SalesManGradeMapper {
    List<SalesmanGrade> listGrade();
}
