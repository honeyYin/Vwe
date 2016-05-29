package com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.OperationRecord;
import com.entity.Paper;

@Repository
public class OperationRecordDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public OperationRecord find(Long id) {
		return entityManager.find(OperationRecord.class, id);
	}
	
	@Transactional
	public OperationRecord save(OperationRecord paper) {
		if (paper.getId() == null) {
			entityManager.persist(paper);
			return paper;
		} else {
			return entityManager.merge(paper);
		}		
	}
	
}
