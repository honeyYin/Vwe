package com.dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.PaperOutLink;
import com.entity.PaperParagraph;

@Repository
public class PaperOutLinkDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public PaperOutLink find(Long id) {
		return entityManager.find(PaperOutLink.class, id);
	}
	@Transactional
	public PaperOutLink save(PaperOutLink paper) {
		if (paper.getId() == null) {
			entityManager.persist(paper);
			return paper;
		} else {
			return entityManager.merge(paper);
		}		
	}
}
