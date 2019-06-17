package com.mediaocean.assessment.retailstore.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mediaocean.assessment.retailstore.beans.BillUpdateInfo;
import com.mediaocean.assessment.retailstore.beans.ProductInfoForBill;
import com.mediaocean.assessment.retailstore.entity.Bill;
import com.mediaocean.assessment.retailstore.entity.BillStatus;
import com.mediaocean.assessment.retailstore.exception.CustomException;
import com.mediaocean.assessment.retailstore.services.BillService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillServiceTest {

	@Autowired
	private BillService billService;
	
	@Test
	public void testCreateBill() {
		Bill o1 = billService.createBill(new Bill(0.0, 0, BillStatus.IN_PROGRESS));
		Bill o2 = billService.getBillById(o1.getId());
		assertThat(o1.getId()).isEqualTo(o2.getId());
	}

	


	@Test
	public void testBillUpdateAddSingleProductCatA() {
		// create a new Bill to update information.
		Bill o1 = billService.createBill(new Bill(0.0, 0, BillStatus.IN_PROGRESS));

		Long billId = o1.getId();
		BillUpdateInfo billupdateInfo = new BillUpdateInfo();
		List<ProductInfoForBill> productsToBeAdded = new ArrayList<ProductInfoForBill>();
		List<ProductInfoForBill> productsToBeRemoved = new ArrayList<ProductInfoForBill>();

		productsToBeAdded.add(new ProductInfoForBill("prod-1001", 2));
		billupdateInfo.setProductsToBeAdded(productsToBeAdded);
		billupdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		billupdateInfo.setStatus(BillStatus.RELEASED);

		System.out.println("billupdateInfo = " + billupdateInfo);
		billService.updateBill(billupdateInfo, billId);
		Bill retrieveUpdatedBill = billService.getBillById(o1.getId());
		System.out.println("retrieveUpdatedBill = " + retrieveUpdatedBill.getNoOfItems() + "  value ="
				+ retrieveUpdatedBill.getTotalValue());
		assertThat(retrieveUpdatedBill.getNoOfItems()).isEqualTo(1);
		assertThat(retrieveUpdatedBill.getTotalValue()).isEqualTo(20 * 2 * 1.1);
	}

	@Test
	public void testBillUpdateAddSingleProductCatB() {

		// create a new bill to update information.
		Bill o1 = billService.createBill(new Bill(0.0, 0, BillStatus.IN_PROGRESS));

		Long billId = o1.getId();
		BillUpdateInfo billupdateInfo = new BillUpdateInfo();
		List<ProductInfoForBill> productsToBeAdded = new ArrayList<ProductInfoForBill>();
		List<ProductInfoForBill> productsToBeRemoved = new ArrayList<ProductInfoForBill>();

		productsToBeAdded.add(new ProductInfoForBill("prod-1002", 2));
		billupdateInfo.setProductsToBeAdded(productsToBeAdded);
		billupdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		billupdateInfo.setStatus(BillStatus.RELEASED);

		System.out.println("billupdateInfo = " + billupdateInfo);
		billService.updateBill(billupdateInfo, billId);
		Bill retrieveUpdatedBill = billService.getBillById(o1.getId());
		System.out.println("retrieveUpdatedBill = " + retrieveUpdatedBill.getNoOfItems() + "  value ="
				+ retrieveUpdatedBill.getTotalValue());
		assertThat(retrieveUpdatedBill.getNoOfItems()).isEqualTo(1);
		assertThat(retrieveUpdatedBill.getTotalValue()).isEqualTo(30 * 2 * 1.2);

	}

	@Test
	public void testBillUpdateAddSingleProductCatC() {

		// create a new bill to update information.
		Bill o1 = billService.createBill(new Bill(0.0, 0, BillStatus.IN_PROGRESS));

		Long billId = o1.getId();
		BillUpdateInfo billupdateInfo = new BillUpdateInfo();
		List<ProductInfoForBill> productsToBeAdded = new ArrayList<ProductInfoForBill>();
		List<ProductInfoForBill> productsToBeRemoved = new ArrayList<ProductInfoForBill>();

		productsToBeAdded.add(new ProductInfoForBill("prod-1003", 2));
		billupdateInfo.setProductsToBeAdded(productsToBeAdded);
		billupdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		billupdateInfo.setStatus(BillStatus.RELEASED);

		System.out.println("billupdateInfo = " + billupdateInfo);
		billService.updateBill(billupdateInfo, billId);
		Bill retrieveUpdatedBill = billService.getBillById(o1.getId());
		System.out.println("retrieveUpdatedBill = " + retrieveUpdatedBill.getNoOfItems() + "  value ="
				+ retrieveUpdatedBill.getTotalValue());
		assertThat(retrieveUpdatedBill.getNoOfItems()).isEqualTo(1);
		assertThat(retrieveUpdatedBill.getTotalValue()).isEqualTo(40 * 2 * 1);
	}

	

}
