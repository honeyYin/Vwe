package com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.CrawlerSite;

@Repository
public class CrawlerSiteDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	public CrawlerSite find(Long id) {
		return entityManager.find(CrawlerSite.class, id);
	}
	@Transactional
	public CrawlerSite save(CrawlerSite cate) {
		if (cate.getId() == null) {
			entityManager.persist(cate);
			return cate;
		} else {
			return entityManager.merge(cate);
		}		
	}
	@SuppressWarnings("unchecked")
	public List<CrawlerSite> findByTitle(String title){
		Query query =  entityManager.createQuery("select p from CrawlerSite p where p.disabled = 0 and p.title = ?1 ");
		query.setParameter(1, title);
		return query.getResultList();	
	}
	@SuppressWarnings("unchecked")
	public List<CrawlerSite> findAllSites(){
		Query query =  entityManager.createQuery("select p from CrawlerSite p where p.disabled = 0 ORDER BY p.title ASC");
		return query.getResultList();	
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public int deleteSite(Long id) {
		Query query = entityManager.createQuery("update CrawlerSite p set p.disabled = 1,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?1");
		query.setParameter(1, id);
		return query.executeUpdate();
	}
}
