package com.myRetail.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.productservice.exception.PriceNotFoundException;
import com.myRetail.productservice.exception.ProductNotFoundException;
import com.myRetail.productservice.modal.PriceData;
import com.myRetail.productservice.modal.Product;
import com.myRetail.productservice.modal.ProductData;
import com.myRetail.productservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*@Anish*/
@Service
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_PATH = "v2/pdp/tcin/";

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Value("${myRetail.product.hostname}")
    private String hostName;

    @Autowired
    ProductMapper prdMapper;

    @Autowired
    private ProductRepository productRepository;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getURIPath(String productId) {
        return hostName + PRODUCT_PATH + productId;
    }

    public void setProductMapper(ProductMapper prdDaoMapper) {
        this.prdMapper = prdDaoMapper;
    }

    @Autowired
    RestTemplate restTemplate;

    private boolean isValid(String productId) {
        if (productId == null && productId.length() > 7) {
            return false;
        }
        return true;
    }

    @Override
    public ProductData getProduct(String pid) throws ProductNotFoundException {
        if (!isValid(pid)) {
            throw new ProductNotFoundException("Invalid pid");
        }
        Product product = productRepository.findByPid(pid);
        if (product == null) {
            product = getProductById(pid);
        }
        return prdMapper.convertToBean(product);
    }

/*price from mock api and and combines it with the product id and name*/
    public PriceData getProductPriceByMockApi() {
        PriceData priceData = getPriceByMockApi();
        return priceData;
    }

    public void saveProductData(String pid) {
      if (!isValid(pid)) {
            throw new ProductNotFoundException("Invalid pid");
        }
        /*Price from mock API.*/
        PriceData priceData = getPriceByMockApi();
        priceData.setCurrencyCode("USD");
        /* combine product price with product id and name*/
        ProductData productData= getProduct(pid);
        productData.setPriceData(priceData);
        Product product = prdMapper.convertToProduct(productData);
        productRepository.save(product);
    }




    @Override
    public ProductData updateProductPrice(Product product) throws ProductNotFoundException {
        String pid = product.getPid();
        if (!isValid(pid)) {
            throw new ProductNotFoundException("Invalid pid");
        }
        //retrieve product from database
        Product existingProduct = productRepository.findByPid(pid);
        if (existingProduct == null) {
            logger.info("Product -" + pid + " is not found in database");
            throw new ProductNotFoundException(String.valueOf(pid) + "is not found in database");
        }
        logger.info("Original Price for " + pid + "-" + existingProduct.getCurrentPrice());
        existingProduct.setCurrentPrice(product.getCurrentPrice());
        //updating the price in database
        Product updatedProduct = productRepository.save(existingProduct);
        logger.info("Updated Price for " + pid + "-" + updatedProduct.getCurrentPrice());
        return prdMapper.convertToBean(product);
    }


    private Product getProductById(String pid) throws ProductNotFoundException {
        String responseBody = null;
        ResponseEntity<String> response = null;
        Product product = new Product(pid);
        List<String> excludes = new ArrayList<>();
        excludes.add("taxonomy");
        //excludes.add("price");
        excludes.add("promotion");
        excludes.add("bulk_ship");
        excludes.add("rating_and_review_reviews");
        excludes.add("rating_and_review_statistics");
        excludes.add("question_answer_statistics");
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(getURIPath(pid)).queryParam("excludes", excludes).build();
        try {
            response = restTemplate.getForEntity(uriComponents.encode().toUri(), String.class);
            responseBody = response.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode prdRootNode = mapper.readTree(responseBody);
            if (prdRootNode != null)
                product.setTitle(prdRootNode.get("product").get("item").get("product_description").get("title").asText());
                product.setCurrentPrice(prdRootNode.get("product").get("price").get("listPrice").get("price").asDouble());
                product.setCurrency_code("USD");
            logger.debug("Product title is " + product);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProductNotFoundException(HttpStatus.NOT_FOUND, pid + " not found");
            }
            if (ex.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new ProductNotFoundException(HttpStatus.FORBIDDEN, "product service forbid  the " + pid);
            } else {
                throw new RuntimeException("product id : " + pid, ex);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Problem parsing JSON String" + responseBody, e);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Unknown exception while trying to fetch from Database for " + pid, e);
        }
        if (!response.getStatusCode().equals(HttpStatus.OK))
            logger.error("Error occurred while retrieving product title, status code: " + response.getStatusCode().value());

        return product;
    }

    public PriceData getPriceByMockApi() throws PriceNotFoundException{
        String responseBody = null;
        ResponseEntity<String> response = null;
        PriceData priceData = null;
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("https://17d809a3-b340-4c37-9aed-9fbee30ab7af.mock.pstmn.io/v2/price").build();
        try {
            response = restTemplate.getForEntity(uriComponents.encode().toUri(), String.class);
            responseBody = response.getBody();
            String price = null;
            ObjectMapper mapper = new ObjectMapper();
            JsonNode prdRootNode = mapper.readTree(responseBody);
            if (prdRootNode != null)
                price = prdRootNode.get("price").get("listPrice").get("price").asText();
            priceData = new PriceData(price, "USD");
            logger.debug("price is " + price);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new PriceNotFoundException(HttpStatus.NOT_FOUND, " not found");
            }
            if (ex.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new PriceNotFoundException(HttpStatus.FORBIDDEN, "price service forbid  the ");
            } else {
                throw new RuntimeException("product id : " + ex);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Problem parsing JSON String" + responseBody, e);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Unknown exception while trying to fetch from Database for " + e);
        }
        if (!response.getStatusCode().equals(HttpStatus.OK))
            logger.error("Error occurred while retrieving product title, status code: " + response.getStatusCode().value());
        return priceData;
    }
}
