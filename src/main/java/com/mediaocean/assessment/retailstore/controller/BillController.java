package com.mediaocean.assessment.retailstore.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mediaocean.assessment.retailstore.beans.BillUpdateInfo;
import com.mediaocean.assessment.retailstore.entity.Bill;
import com.mediaocean.assessment.retailstore.entity.BillStatus;
import com.mediaocean.assessment.retailstore.services.BillService;

@RestController
public class BillController {
	@Autowired
	private BillService billService;

	final Logger logger = LoggerFactory.getLogger(getClass());

	
	@PostMapping(value = "/bills")
	public ResponseEntity<Object> createBill() {
		logger.info("Request recieved to create Bill = ");
		Bill bill = billService.createBill(new Bill(0.0, 0, BillStatus.IN_PROGRESS));
		logger.info("Created Bill with id = " + bill.getId());
		
		URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bill.getId())
				.toUri();
		logger.info("Setting header url with newly created Bill= " + bill.getId());
		return ResponseEntity.created(newPollUri).build();
	}

	@GetMapping(value = "/bills/{id}")
	public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
		return new ResponseEntity<>(billService.getBillById(id), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/bills")
	public ResponseEntity<Iterable<Bill>> getAllBills() {
		return new ResponseEntity<>(billService.getAllBills(), HttpStatus.OK);
	}

	
	@PutMapping(value = "/bills/{id}")
	public ResponseEntity<Bill> updateBill(@RequestBody BillUpdateInfo billUpdateInfo, @PathVariable Long id) {
		Bill updated = billService.updateBill(billUpdateInfo, id);
		logger.info("Request recieved =  " + billUpdateInfo);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
	

	@DeleteMapping(value = "/bills/{id}")
	public ResponseEntity<String> deleteBill(@PathVariable Long id) {
		billService.deleteBill(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}


}
