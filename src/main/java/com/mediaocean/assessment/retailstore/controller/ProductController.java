package com.mediaocean.assessment.retailstore.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mediaocean.assessment.retailstore.beans.ProductInfo;
import com.mediaocean.assessment.retailstore.entity.Product;
import com.mediaocean.assessment.retailstore.services.ProductService;

import io.swagger.annotations.ApiParam;

// Entity Beans are used and returned by this call to web layer. Ideally they should be different.

@RestController
public class ProductController {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductService productService;

	@PostMapping(value = "/products")
	public ResponseEntity<Object> createProduct(
			@Valid @RequestBody ProductInfo productInfo) {
		logger.info("Input recieved to create product = " + productInfo);
		Product product = productService.createProduct(productInfo);
		logger.info("Created product with id = " + product.getId());

		URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId())
				.toUri();
		logger.info("Setting header url with newly created product= " + product.getId());
		return ResponseEntity.created(newPollUri).build();
	}



	@GetMapping(value = "/products")
	public ResponseEntity<Iterable<Product>> getAllProducts() {
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}

	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Product> getProductById(
			@ApiParam(value = "id of a particular product", required = true) @PathVariable Long id) {
		return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
	}

	

	@PutMapping(value = "/products/{id}")
	public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductInfo productInfo, @PathVariable Long id) {
		Product prod = productService.updateProduct(productInfo, id);
		logger.info("updated product id = " + prod.getId());
		return new ResponseEntity<>(prod, HttpStatus.OK);
	}

}
