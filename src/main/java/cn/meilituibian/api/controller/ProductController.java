package cn.meilituibian.api.controller;

import cn.meilituibian.api.common.Constants;
import cn.meilituibian.api.domain.Product;
import cn.meilituibian.api.domain.ProductType;
import cn.meilituibian.api.service.ProductService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "产品详情")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @RequestMapping(value = "/types", method = RequestMethod.GET)
    @ApiOperation(value = "产品类型")
    public ResponseEntity<?> getProductTypes() {
        List<ProductType> list = new ArrayList();
        list.add(new ProductType(1L, "面膜"));
        list.add(new ProductType(2L, "眼霜"));
        list.add(new ProductType(3L, "洗面奶"));
        list.add(new ProductType(5L, "粉底液"));
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }

}
