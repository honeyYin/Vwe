package com.dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entity.PaperParagraph;

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
	@Transactional
	@SuppressWarnings("unchecked")
	public int delete(Long id) {
		Query query = entityManager.createQuery("update PaperParagraph p set p.disabled = 1,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?1");
		query.setParameter(1, id);
		return query.executeUpdate();
	}
}
