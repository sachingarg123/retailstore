package com.mediaocean.assessment.retailstore.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediaocean.assessment.retailstore.beans.ProductInfo;
import com.mediaocean.assessment.retailstore.entity.Product;
import com.mediaocean.assessment.retailstore.exception.CustomException;
import com.mediaocean.assessment.retailstore.repository.ProductRepository;


/**
 * Product Service for Product Controller
 * @author Sachin Garg
 *
 */
@Service
public class ProductService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProductRepository productRepo;
	

	/**
	 * Method to create Product
	 * @param productInfo
	 * @return Product
	 */
	public Product createProduct(ProductInfo productInfo) {
		logger.info("Input recieved to create product = " + productInfo);
		verifyIfProductExists(productInfo.getBarCodeId());
		Product product = new Product();
		product.setBarCodeId(productInfo.getBarCodeId());
		product.setName(productInfo.getName());
		product.setProductCategory(productInfo.getProductCategory());
		product.setRate(productInfo.getRate());

		product = productRepo.save(product);
		logger.info("Created product with id = " + product.getId());
		return product;

	}

	/**
	 * Method to return all products
	 * @return Iterable<Product>
	 */
	public Iterable<Product> getAllProducts() {
		Iterable<Product> products = productRepo.findAll();
		logger.info("returning all products");
		return products;
	}

	/**
	 * Method to return all products
	 * @param id
	 * @return Product
	 */
	public Product getProductById(Long id) {
		verifyProductExists(id);
		Optional<Product> productData = productRepo.findById(id);
		return productData.get();
	}

	/**
	 * Method to update Product Details
	 * @param productInfo
	 * @param id
	 * @return Product
	 */
	public Product updateProduct(ProductInfo productInfo, Long id) {
		verifyProductExists(id);
		Optional<Product> productData = productRepo.findById(id);
		Product product = productData.get();
		product.setBarCodeId(productInfo.getBarCodeId());
		product.setName(productInfo.getName());
		product.setProductCategory(productInfo.getProductCategory());
		product.setRate(productInfo.getRate());
		Product p = productRepo.save(product);
		logger.info("updated product id = " + product.getId());
		return p;
	}

	/**
	 * Method to verify product if exist using bar code
	 * @param barCodeId
	 */
	private void verifyIfProductExists(String barCodeId) {
		List<Product> productsByBarCodeID = productRepo.findByBarCodeId(barCodeId);
		if (null != productsByBarCodeID && !productsByBarCodeID.isEmpty()) {
			logger.info("Problem with input data: BarCode ID  " + barCodeId + " already exists in Product Master");
			throw new CustomException(
					"Problem with input data: BarCode ID  " + barCodeId + " already exists in Product Master");
		}
	}

	/**
	 * Method to verify if product exist 
	 * @param id
	 */
	private void verifyProductExists(Long id) {
		logger.info("Verifying if the product exists with an id = " + id);
		Optional<Product> productData = productRepo.findById(id);
		Product product = productData.get();
		if (product == null) {
			throw new CustomException("Product with id " + id + " not found");
		}
	}

}
