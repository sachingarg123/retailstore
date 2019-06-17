package com.mediaocean.assessment.retailstore.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class LineItem {

	@Id
	@GeneratedValue
	private long id;

	@OneToOne(fetch = FetchType.EAGER)
	private Product product;

	private long quantity;

	public LineItem() {
		super();
	}

	public LineItem(Product product, int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}

	public long getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "LineItem [id=" + id + ", product=" + product + ", quantity=" + quantity + "]";
	}

	

}
