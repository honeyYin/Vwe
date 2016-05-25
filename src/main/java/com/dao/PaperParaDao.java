package com.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entity.Paper;
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
	@SuppressWarnings("unchecked")
	public List<PaperParagraph> getParaBySection(Long sectionId) {
		Query query =  entityManager.createQuery("select p from PaperParagraph p where p.disabled = 0 and p.sectionId = ?1 ORDER BY p.orderNum ASC ");
		query.setParameter(1, sectionId);
		return query.getResultList();
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public int deleteImg(Long id) {
		Query query = entityManager.createQuery("update PaperParagraph p set p.imgUrl = null,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?1");
		query.setParameter(1, id);
		return query.executeUpdate();
	}
}
