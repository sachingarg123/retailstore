package com.mediaocean.assessment.retailstore.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mediaocean.assessment.retailstore.beans.ProductInfo;
import com.mediaocean.assessment.retailstore.entity.Product;
import com.mediaocean.assessment.retailstore.entity.ProductCategory;
import com.mediaocean.assessment.retailstore.exception.CustomException;
import com.mediaocean.assessment.retailstore.services.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@Test
	public void testProductCreate(){
		Product p1 = productService.createProduct(new ProductInfo("abc-0001", 20.0, "Tomato", ProductCategory.A));
		Product p2 = productService.getProductById(p1.getId());
		assertThat(p1.getId()).isEqualTo(p2.getId());
	}

	@Test
	public void testProductUpdate(){
		Product p1 = productService.createProduct(new ProductInfo("abc-0002", 20.0, "Tomato", ProductCategory.A));
		Product p2 = productService.updateProduct(new ProductInfo("abc-0003", 30.0, "Potato", ProductCategory.B),p1.getId());
		Product p3 = productService.getProductById(p1.getId());
		assertThat(p3.getId()).isEqualTo(p2.getId());
		
		assertThat(p3.getBarCodeId()).isEqualTo(p2.getBarCodeId());
		assertThat(p3.getRate()).isEqualTo(p2.getRate());
		assertThat(p3.getProductCategory()).isEqualTo(p2.getProductCategory());
	}
	

	

}
