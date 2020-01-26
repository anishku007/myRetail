package com.myRetail.productservice.service;

import com.myRetail.productservice.modal.PriceData;
import com.myRetail.productservice.modal.Product;
import com.myRetail.productservice.modal.ProductData;

public interface ProductService {

    ProductData getProduct(String id) ;

    PriceData getProductPriceByMockApi() ;

    public void saveProductData(String pid);

    ProductData updateProductPrice(Product product);
}
