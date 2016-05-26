package com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Paper;
import com.entity.PaperImage;

@Repository
public class PaperImageDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	public PaperImage find(Long id) {
		return entityManager.find(PaperImage.class, id);
	}
	
	@Transactional
	public PaperImage save(PaperImage cate) {
		if (cate.getId() == null) {
			entityManager.persist(cate);
			return cate;
		} else {
			return entityManager.merge(cate);
		}		
	}
	
	@SuppressWarnings("unchecked")
	public List<PaperImage> findByUrl(String url) {
		Query query =  entityManager.createQuery("select p from PaperImage p where p.imgUrl = ?1");
		query.setParameter(1, url);
		return query.getResultList();
	}
	
}
