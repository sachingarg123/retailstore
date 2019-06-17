package com.mediaocean.assessment.retailstore.beans;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;


import com.mediaocean.assessment.retailstore.entity.ProductCategory;

public class ProductInfo {

	@NotNull
	private String barCodeId;

	@NotNull
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ProductCategory productCategory;

	@NotNull
	@DecimalMin(value = "0.1")
	private double rate;

	public ProductInfo() {
		super();
	}

	public ProductInfo(String barCodeId, double rate, String name, ProductCategory productCategory) {
		super();
		this.barCodeId = barCodeId;
		this.name = name;
		this.productCategory = productCategory;
		this.rate = rate;
	}

	public String getBarCodeId() {
		return barCodeId;
	}

	public String getName() {
		return name;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public double getRate() {
		return rate;
	}

	public void setBarCodeId(String barCodeId) {
		this.barCodeId = barCodeId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "ProductInfo [barCodeId=" + barCodeId + ", name=" + name + ", productCategory=" + productCategory
				+ ", rate=" + rate + "]";
	}

	

}
