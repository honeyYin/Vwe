package com.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.PaperSection;

@Repository
public class PaperSectionDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public PaperSection find(Long id) {
		return entityManager.find(PaperSection.class, id);
	}
	@Transactional
	public PaperSection save(PaperSection paper) {
		if (paper.getId() == null) {
			entityManager.persist(paper);
			return paper;
		} else {
			return entityManager.merge(paper);
		}		
	}
}
