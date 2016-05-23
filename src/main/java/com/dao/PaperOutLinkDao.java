package com.dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	@Transactional
	@SuppressWarnings("unchecked")
	public int delete(Long id) {
		Query query = entityManager.createQuery("update PaperOutLink p set p.disabled = 1,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?1");
		query.setParameter(1, id);
		return query.executeUpdate();
	}
}
