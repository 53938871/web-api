package cn.meilituibian.api.controller;

import cn.meilituibian.api.common.Constants;
import cn.meilituibian.api.domain.Article;
import cn.meilituibian.api.domain.Comment;
import cn.meilituibian.api.service.ArticleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value="文章信息", description="文章信息")
@RequestMapping(value = "/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询文章")
    public  ResponseEntity<?> getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        return new ResponseEntity<Article>(article, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询文章")
    public ResponseEntity<?> selectArticles(@RequestParam int pageNo){
        Page<Article> articles = articleService.selectArticles(pageNo, Constants.PAGE_SIZE);
        PageInfo<Article> result = new PageInfo<>(articles);
        return new ResponseEntity<PageInfo<Article>>(result, HttpStatus.OK);
    }
}
