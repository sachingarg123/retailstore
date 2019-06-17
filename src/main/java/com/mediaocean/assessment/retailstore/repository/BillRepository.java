package com.mediaocean.assessment.retailstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.mediaocean.assessment.retailstore.entity.Bill;

public interface BillRepository extends CrudRepository<Bill, Long> {

}
