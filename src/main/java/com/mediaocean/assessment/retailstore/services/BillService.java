package com.mediaocean.assessment.retailstore.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediaocean.assessment.retailstore.beans.BillUpdateInfo;
import com.mediaocean.assessment.retailstore.beans.ProductInfoForBill;
import com.mediaocean.assessment.retailstore.entity.Bill;
import com.mediaocean.assessment.retailstore.entity.LineItem;
import com.mediaocean.assessment.retailstore.entity.Product;
import com.mediaocean.assessment.retailstore.entity.ProductCategory;
import com.mediaocean.assessment.retailstore.exception.CustomException;
import com.mediaocean.assessment.retailstore.repository.BillRepository;
import com.mediaocean.assessment.retailstore.repository.LineItemRepository;
import com.mediaocean.assessment.retailstore.repository.ProductRepository;

/**
 * Service class for Bill
 * 
 * @author Sachin Garg
 *
 */
@Service
public class BillService {

	@Autowired
	private BillRepository billRepo;

	@Autowired
	private LineItemRepository lineItemRepo;

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductRepository productRepo;

	/**
	 * Service Method to create Bill
	 * 
	 * @param bill
	 * @return Bill
	 */
	public Bill createBill(Bill bill) {
		logger.info("Input recieved to create Bill = " + bill);
		Bill bill1 = billRepo.save(bill);
		logger.info("Created product with id = " + bill1.getId());
		return bill1;

	}

	/**
	 * Method to give Bill Details
	 * 
	 * @param id
	 * @return Bill
	 */
	public Bill getBillById(Long id) {
		Optional<Bill> bill = billRepo.findById(id);
		if (bill == null) {
			throw new CustomException("Bill with id " + id + " not found");
		}
		return bill.get();
	}

	/**
	 * Method to delete Bill
	 * 
	 * @param id
	 */
	public void deleteBill(Long id) {
		Optional<Bill> bill = billRepo.findById(id);
		if (bill == null) {
			throw new CustomException("Bill with id " + id + " not found");
		}
		billRepo.deleteById(id);
	}

	/**
	 * Method to return all Bills
	 * 
	 * @return Iterable<Bill>
	 */
	public Iterable<Bill> getAllBills() {
		Iterable<Bill> bill = billRepo.findAll();
		logger.info("returning all products");
		return bill;
	}

	/**
	 * Method to update Bill Details
	 * 
	 * @param billUpdateInfo
	 * @param billId
	 * @return Bill
	 */
	public Bill updateBill(BillUpdateInfo billUpdateInfo, Long billId) {

		logger.info("Request recieved for update of  : " + billId);
		if (null == billUpdateInfo) {
			throw new CustomException("There is no information to be updated for id " + billId);
		}

		Optional<Bill> bill = billRepo.findById(billId);
		if (bill == null) {
			throw new CustomException("Bill with id " + billId + " not found");
		}

		// To add line items
		logger.info("Processing products to be added");
		if (null != billUpdateInfo.getProductsToBeAdded()) {
			List<ProductInfoForBill> prodToBeAdded = billUpdateInfo.getProductsToBeAdded();
			Iterator<ProductInfoForBill> prodToBeAddedIter = prodToBeAdded.iterator();
			while (prodToBeAddedIter.hasNext()) {
				ProductInfoForBill pInfo = prodToBeAddedIter.next();
				logger.debug("Product to be added : " + pInfo);
				addProductToBill(pInfo.getBarCodeId(), pInfo.getQuantity(), bill.get());
			}
		}

		// to remove line items
		logger.info("Processing products to be removed");
		if (null != billUpdateInfo.getProductsToBeRemoved()) {
			List<ProductInfoForBill> prodToBeRemoved = billUpdateInfo.getProductsToBeRemoved();
			Iterator<ProductInfoForBill> prodToBeRemovedIter = prodToBeRemoved.iterator();
			while (prodToBeRemovedIter.hasNext()) {
				ProductInfoForBill pInfo = prodToBeRemovedIter.next();
				logger.info("Product to be removed : " + pInfo);
				removeProductFromBill(bill.get(), pInfo.getBarCodeId());
			}
		}

		Optional<Bill> newBill = billRepo.findById(billId);
		if (newBill == null) {
			throw new CustomException("Bill with id " + billId + " not found");
		}
		newBill.get().setBillStatus(billUpdateInfo.getStatus()); // Update Bill Status
		logger.info("Computing total for the bill");
		computeTotalValues(newBill.get());
		return newBill.get();
	}

	/**
	 * Method to add Product in Bill
	 * @param barCodeId
	 * @param quantity
	 * @param bill
	 * @return Bill
	 */
	private Bill addProductToBill(String barCodeId, int quantity, Bill bill) {

		Product selectedProduct1 = verifyIfProductExists(barCodeId);
		LineItem l1 = new LineItem(selectedProduct1, quantity);
		lineItemRepo.save(l1);
		List<LineItem> currentLineItems = bill.getLineItems();
		if (currentLineItems != null) {
			logger.debug("There are lineItems in the bill already..Adding to list of items");
			LineItem existingLi = getLineItemWithBarCodeId(barCodeId, currentLineItems);
			if (existingLi == null) {
				bill.getLineItems().add(l1);
			} else {
				long newQty = existingLi.getQuantity() + quantity;
				existingLi.setQuantity(newQty);
			}
		} else {
			logger.debug("There are no line items currently in the Bill..Creating new list");
			currentLineItems = new ArrayList<>();
			currentLineItems.add(l1);
			bill.setLineItems(currentLineItems);
		}
		billRepo.save(bill);
		logger.debug("Product Added Successfully  to Bill : " + l1.getId());
		return bill;
	}

	/**
	 * Method to remove product from Bill
	 * @param bill
	 * @param barCodeId
	 * @return Bill
	 */
	private Bill removeProductFromBill(Bill bill, String barCodeId) {

		List<LineItem> currentLineItems = bill.getLineItems();
		verifyIfProductExists(barCodeId);
		logger.info("Bar Code Id to be removed  = " + barCodeId);

		if (currentLineItems != null && !currentLineItems.isEmpty()) {
			LineItem lineItem = getLineItemWithBarCodeId(barCodeId, currentLineItems);
			if (null == lineItem) {
				logger.info(
						"Product does not exist in current list of products. Cannot remove productId : " + barCodeId);
				throw new CustomException(
						"Problem with input data: Product does not exist in current list of products. Cannot remove product with BarCode ID "
								+ barCodeId);

			}
			logger.info("line item to be deleted " + lineItem);
			currentLineItems.remove(lineItem);
			bill.setLineItems(currentLineItems);
			billRepo.save(bill);
		    lineItemRepo.delete(lineItem); 
		} else {
			logger.info("There are no line items currently in the Bill..Cannot remove productId : " + barCodeId);
			throw new CustomException(
					"Problem with input data: There are no line items currently in the Bill. Cannot remove product with BarCode ID "
							+ barCodeId);
		}
		billRepo.save(bill);
		return bill;
	}

	
	/**
	 * Method to compute sales tax and total cost on Bill
	 * @param bill
	 */
	private void computeTotalValues(Bill bill) {

		int noOfItems = 0;
		double totalValue = 0;
		double totalCost = 0;

		if (null != bill.getLineItems()) {
			List<LineItem> lineItems = bill.getLineItems();
			Iterator<LineItem> lineItemsIter = lineItems.iterator();
			while (lineItemsIter.hasNext()) {
				LineItem li = lineItemsIter.next();
				double saleValue = computeValueForItem(li.getQuantity(), li.getProduct().getProductCategory(),
						li.getProduct().getRate());
				logger.debug("SaleValue &  Line Item  : " + saleValue + "   " + li);
				totalValue += saleValue;
				totalCost += li.getQuantity() * li.getProduct().getRate();
				noOfItems++;
			}
		}
		bill.setNoOfItems(noOfItems);
		bill.setTotalValue(totalValue);
		bill.setTotalCost(totalCost);
		bill.setTotalTax(totalValue - totalCost);
		billRepo.save(bill);
	}

	/**
	 * Method to compute total cost with sales tax on a product
	 * @param quantity
	 * @param productCategory
	 * @param rate
	 * @return salesValue
	 */
	private double computeValueForItem(long quantity, ProductCategory productCategory, double rate) {
		logger.debug("productCategory : " + productCategory + "  quantity = " + quantity + "  rate = " + rate);
		double saleValue = 0;
		if (productCategory.equals(ProductCategory.A)) {
			saleValue = quantity * rate * 1.1; // 10% levy

		} else if (productCategory.equals(ProductCategory.B)) {
			saleValue = quantity * rate * 1.2; // 20% levy

		} else if (productCategory.equals(ProductCategory.C)) {
			saleValue = quantity * rate;
		}
		return saleValue;
	}

	/**
	 * Method to verify if product exist
	 * @param barCodeId
	 * @return Product
	 */
	private Product verifyIfProductExists(String barCodeId) {
		List<Product> productsByBarCodeID = productRepo.findByBarCodeId(barCodeId);
		if (null == productsByBarCodeID || productsByBarCodeID.isEmpty()) {
			logger.info("Problem with input data: BarCode ID  " + barCodeId + " does not exist in Product Master");
			throw new CustomException(
					"Problem with input data: BarCode ID " + barCodeId + " does not exist in Product Master");
		} else {
			logger.debug("selectedProduct1  = " + productsByBarCodeID.get(0).getId());
		}
		return productsByBarCodeID.get(0);
	}

	
	/**
	 * Method to verify of Product already exist in Line Item
	 * @param barCodeId
	 * @param currentLineItems
	 * @return LineItem
	 */
	private LineItem getLineItemWithBarCodeId(String barCodeId, List<LineItem> currentLineItems) {
		for (int i = 0; i < currentLineItems.size(); i++) {
			LineItem li = currentLineItems.get(i);
			if (barCodeId.equals(li.getProduct().getBarCodeId())) {
				logger.info(" Line Items has product: " + barCodeId);
				return li;
			}
		}
		logger.info(" Current list of Line Items do not have product: " + barCodeId);
		return null;
	}

}
