package com.mediaocean.assessment.retailstore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mediaocean.assessment.retailstore.entity.LineItem;

public interface LineItemRepository extends CrudRepository<LineItem, Long> {
	
	public List<LineItem> findByProduct_id(long prodId);

}
