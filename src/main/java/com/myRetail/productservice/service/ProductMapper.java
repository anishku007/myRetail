package com.myRetail.productservice.service;

import com.myRetail.productservice.modal.PriceData;
import com.myRetail.productservice.modal.Product;
import com.myRetail.productservice.modal.ProductData;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductData convertToBean(Product product) {

        ProductData productData = null;
        if (product != null) {
            productData = new ProductData();

            productData.setId(Integer.parseInt(product.getPid()));
            productData.setTitle(product.getTitle());

            PriceData priceData = new PriceData();
            priceData.setCurrencyCode(product.getCurrency_code());
            priceData.setPrice(String.valueOf(product.getCurrentPrice()));
            productData.setPriceData(priceData);
        }
        return productData;
    }

    public Product convertToDao(ProductData productData) {
        Product product = new Product(String.valueOf(productData.getId()));
        product.setCurrentPrice(Double.valueOf(productData.getPriceData().getPrice()));
        product.setCurrency_code(productData.getPriceData().getCurrencyCode());
        return product;
    }

    public Product convertToProduct(ProductData productData) {
        Product product = new Product(String.valueOf(productData.getId()));
        product.setTitle(productData.getTitle());
        product.setCurrentPrice(Double.valueOf(productData.getPriceData().getPrice()));
        product.setCurrency_code(productData.getPriceData().getCurrencyCode());
        return product;
    }

}
