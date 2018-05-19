package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.Article;
import cn.meilituibian.api.mapper.ArticleMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    public Article getArticleById(Long id) {
        return articleMapper.getArticleById(id);
    }

    public Page<Article> selectArticles(int pageNo, int pageSize){
        PageHelper.startPage(pageNo, pageSize);
        return articleMapper.selectArticles();
    }
}
