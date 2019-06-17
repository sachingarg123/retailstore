package com.mediaocean.assessment.retailstore.beans;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.mediaocean.assessment.retailstore.entity.BillStatus;

public class BillUpdateInfo {
	private List<ProductInfoForBill> productsToBeAdded;
	private List<ProductInfoForBill> productsToBeRemoved;

	@NotNull
	private BillStatus status;

	public BillUpdateInfo() {
		super();

	}

	public List<ProductInfoForBill> getProductsToBeAdded() {
		return productsToBeAdded;
	}

	public List<ProductInfoForBill> getProductsToBeRemoved() {
		return productsToBeRemoved;
	}

	public BillStatus getStatus() {
		return status;
	}

	public void setProductsToBeAdded(List<ProductInfoForBill> productsToBeAdded) {
		this.productsToBeAdded = productsToBeAdded;
	}

	public void setProductsToBeRemoved(List<ProductInfoForBill> productsToBeRemoved) {
		this.productsToBeRemoved = productsToBeRemoved;
	}

	public void setStatus(BillStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BillUpdateInfo [productsToBeAdded=" + productsToBeAdded + ", productsToBeRemoved=" + productsToBeRemoved
				+ ", status=" + status + "]";
	}

	

}
