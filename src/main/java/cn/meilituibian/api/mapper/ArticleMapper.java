package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.Article;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    Page<Article> selectArticles();

    Article getArticleById(Long id);
}
