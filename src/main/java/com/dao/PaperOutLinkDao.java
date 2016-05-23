package com.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Paper;
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
	@SuppressWarnings("unchecked")
	public List<PaperOutLink> getOutLinkBySection(Long sectionId) {
		Query query =  entityManager.createQuery("select p from PaperOutLink p where p.disabled = 0 and p.sectionId = ?1 ORDER BY p.orderNum ASC ");
		query.setParameter(1, sectionId);
		return query.getResultList();
	}
}
