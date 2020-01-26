package com.myretail.productServiceTest.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.myRetail.productservice.controller.ProductController;
import com.myRetail.productservice.exception.ProductNotFoundException;
import com.myRetail.productservice.modal.Product;
import com.myRetail.productservice.modal.ProductData;
import com.myRetail.productservice.service.ProductMapper;
import com.myRetail.productservice.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
	
	@InjectMocks
	private ProductController productController = new ProductController();
    
    @Mock
    private ProductService mockProductService;
    
    @Mock
    private ProductMapper mockProductMapper;

	@Test
	public void getProduct_Success() throws ProductNotFoundException {
		when(mockProductService.getProduct(String.valueOf(anyInt())))
	    .thenReturn(any(ProductData.class));
		ResponseEntity<ProductData> productEntity = productController.getProductDetails("13860428");
		assertEquals(HttpStatus.OK, productEntity.getStatusCode());
	}

	@Test
	public void testUpdateProductPrice() throws ProductNotFoundException {	
		when(mockProductMapper.convertToDao(any(ProductData.class))).thenReturn(any(Product.class));
		productController.updateProductPrice(new ProductData(), 1386);
		verify(mockProductMapper).convertToDao(any(ProductData.class));
	}

}