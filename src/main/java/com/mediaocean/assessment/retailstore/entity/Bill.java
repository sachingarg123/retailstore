package com.mediaocean.assessment.retailstore.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;



@Entity
public class Bill {

	@Id
	@GeneratedValue
	private long id;
	
	private int noOfItems;
	private double totalCost;

	private double totalTax;

	private double totalValue;

	@NotNull
	@Enumerated(EnumType.STRING)
	private BillStatus billStatus;

	@OneToMany(cascade=CascadeType.REMOVE,fetch = FetchType.EAGER)
	private List<LineItem> lineItems;

	public Bill() {
		super();
	}

	public Bill(double totalValue, int noOfItems, BillStatus billStatus) {
		super();
		this.totalValue = totalValue;
		this.noOfItems = noOfItems;
		this.billStatus = billStatus;
	}

	public Bill(List<LineItem> lineItems) {
		super();
		this.lineItems = lineItems;
	}

	public BillStatus getBillStatus() {
		return billStatus;
	}

	public long getId() {
		return id;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public int getNoOfItems() {
		return noOfItems;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setBillStatus(BillStatus billStatus) {
		this.billStatus = billStatus;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", noOfItems=" + noOfItems + ", totalCost=" + totalCost + ", totalTax=" + totalTax
				+ ", totalValue=" + totalValue + ", billStatus=" + billStatus + ", lineItems=" + lineItems + "]";
	}

	

}
