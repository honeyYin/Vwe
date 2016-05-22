package com.dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entity.PaperParagraph;
import com.entity.PaperSection;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PaperParaDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public PaperParagraph find(Long id) {
		return entityManager.find(PaperParagraph.class, id);
	}
	@Transactional
	public PaperParagraph save(PaperParagraph paper) {
		if (paper.getId() == null) {
			entityManager.persist(paper);
			return paper;
		} else {
			return entityManager.merge(paper);
		}		
	}
}
