package cn.meilituibian.api.controller;

import cn.meilituibian.api.common.Constants;
import cn.meilituibian.api.domain.Product;
import cn.meilituibian.api.service.ProductService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="product list", description="产品列表")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "产品列表")
    public ResponseEntity<?> selectArticles(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo){
        Page<Product> articles = productService.list(pageNo, Constants.PAGE_SIZE);
        PageInfo<Product> result = new PageInfo<>(articles);
        return new ResponseEntity<PageInfo<Product>>(result, HttpStatus.OK);
    }
}
