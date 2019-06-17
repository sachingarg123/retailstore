package com.mediaocean.assessment.retailstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mediaocean.assessment.retailstore.beans.ProductInfo;
import com.mediaocean.assessment.retailstore.entity.ProductCategory;
import com.mediaocean.assessment.retailstore.services.ProductService;

@Component
public class SampleDataSetupRunner implements CommandLineRunner {


	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductService productService;

	@Override
	public void run(String... arg0) throws Exception {
		logger.info("Inside Runner..");
		setUpProductData();
		logger.info("Exiting Runner.. ");
	}


	private void setUpProductData() {
		productService.createProduct(new ProductInfo("prod-1001", 20.0, "Tomato", ProductCategory.A));
		productService.createProduct(new ProductInfo("prod-1002", 30.0, "Onion", ProductCategory.B));
		productService.createProduct(new ProductInfo("prod-1003", 40.0, "Potato", ProductCategory.C));
		productService.createProduct(new ProductInfo("prod-1004", 50.0, "Bread", ProductCategory.A));
		productService.createProduct(new ProductInfo("prod-1005", 60.0, "Apples", ProductCategory.B));
		productService.createProduct(new ProductInfo("prod-1006", 70.0, "Banana", ProductCategory.C));
		productService.createProduct(new ProductInfo("prod-1007", 80.0, "Strawberry", ProductCategory.A));
		productService.createProduct(new ProductInfo("prod-1008", 90.0, "Apricot", ProductCategory.B));
		productService.createProduct(new ProductInfo("prod-1009", 100.0, "Raisins", ProductCategory.C));
		productService.createProduct(new ProductInfo("prod-1010", 110.0, "CashewNut", ProductCategory.A));
	}
}
