package com.mediaocean.assessment.retailstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;



@Entity
public class Product {

	@Id
	@GeneratedValue
	private long id;
	
	@NotNull
	@Column(unique = true)
	private String barCodeId;

	@NotNull
	private String name;
	
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private ProductCategory productCategory;

	@NotNull
	private double rate;

	public Product() {
		super();
	}

	public Product(String barCodeId, double rate, String name, ProductCategory productCategory) {
		super();
		this.barCodeId = barCodeId;
		this.rate = rate;
		this.name = name;
		this.productCategory = productCategory;
	}

	public String getBarCodeId() {
		return barCodeId;
	}

	public long getId() {
		return id;
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

	public void setId(long id) {
		this.id = id;
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
		return "Product [id=" + id + ", barCodeId=" + barCodeId + ", name=" + name + ", productCategory="
				+ productCategory + ", rate=" + rate + "]";
	}

	

}
