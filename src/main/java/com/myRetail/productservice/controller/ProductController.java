package com.myRetail.productservice.controller;

import com.myRetail.productservice.modal.PriceData;
import com.myRetail.productservice.modal.Product;
import com.myRetail.productservice.modal.ProductData;
import com.myRetail.productservice.service.ProductMapper;
import com.myRetail.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/*@Anish*/
@RestController
@RequestMapping("/myRetail")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper prdDaoMapper;

    @GetMapping(path = "/products/{productId}", produces = "application/json")
    public ResponseEntity<ProductData> getProductDetails(@PathVariable String productId) {
        ProductData productData = productService.getProduct(productId);
        return new ResponseEntity<>(productData, HttpStatus.OK);
    }

    /*Using Price api mock*/
    @GetMapping(path = "/product/{productId}/price", produces = "application/json")
    public ResponseEntity<ProductData> getProductDetailsWithPrice(@PathVariable String productId) {
        PriceData priceData = productService.getProductPriceByMockApi();
        ProductData productData = productService.getProduct(productId);
        productData.setPriceData(priceData);
        return new ResponseEntity<>(productData, HttpStatus.OK);
    }

    /*Using Price api mock*/
    @PostMapping(path = "/products/{productId}", produces = "application/json")
    public ResponseEntity<String> saveProductDetailsWithPrice(@PathVariable String productId) {
        productService.saveProductData(productId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productId)
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(location).build();
    }

    @PutMapping(path = "/products/{productId}", produces = "application/json")
    public ResponseEntity<String> updateProductPrice(@RequestBody ProductData productRequest, @PathVariable int productId) {
        Product product = prdDaoMapper.convertToDao(productRequest);
        productService.updateProductPrice(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productId)
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).build();
    }
}
