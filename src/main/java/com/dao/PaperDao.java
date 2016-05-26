package com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entity.Paper;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PaperDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Paper find(Long id) {
		return entityManager.find(Paper.class, id);
	}
	@SuppressWarnings("unchecked")
	public Long getAllPageSize(){
		Query query =  entityManager.createQuery("select count(p.id) from Paper p where p.disabled = 0");
		return (Long)query.getSingleResult();
	}
	@SuppressWarnings("unchecked")
	public Long getCategoryPageSize(long cateId){
		Query query =  entityManager.createQuery("select count(p.id) from Paper p where p.disabled = 0 and p.channelId = ?1");
		query.setParameter(1, cateId);
		return (Long)query.getSingleResult();
	}
	@SuppressWarnings("unchecked")
	public Long getfAllPageSize(){
		Query query =  entityManager.createQuery("select count(p.id) from Paper p where p.disabled = 0 and p.hasAudit = true");
		return (Long)query.getSingleResult();
	}
	@SuppressWarnings("unchecked")
	public Long getfCategoryPageSize(long cateId){
		Query query =  entityManager.createQuery("select count(p.id) from Paper p where p.disabled = 0 and p.hasAudit = true and p.channelId = ?1");
		query.setParameter(1, cateId);
		return (Long)query.getSingleResult();
	}
	@SuppressWarnings("unchecked")
	public List<Paper> getPaperByPage(int limit,int offset) {
		Query query =  entityManager.createQuery("select p from Paper p where p.disabled = 0 ORDER BY p.hasAudit DESC,p.isTop DESC,p.updateTime DESC ");
		query.setFirstResult(offset);//设置查询结果的开始记录数
		query.setMaxResults(limit);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Paper> fgetPaperByPage(int limit,int offset) {
		Query query =  entityManager.createQuery("select p from Paper p where p.disabled = 0 and p.hasAudit = true ORDER BY p.hasAudit DESC,p.isTop DESC,p.updateTime DESC ");
		query.setFirstResult(offset);//设置查询结果的开始记录数
		query.setMaxResults(limit);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Paper> getPaperByCategory(long cateId,int limit,int offset) {
		Query query =  entityManager.createQuery("select p from Paper p where p.disabled = 0 and p.channelId = ?1 ORDER BY p.hasAudit DESC,p.isTop DESC,p.updateTime DESC ");
		query.setParameter(1, cateId);
		query.setFirstResult(offset);//设置查询结果的开始记录数
		query.setMaxResults(limit);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Paper> getRecPaperByCategory(long cateId,long paperId,int limit,int offset) {
		Query query =  entityManager.createQuery("select p from Paper p where p.disabled = 0 and p.channelId = ?1 and p.id != ?2 ORDER BY p.hasAudit DESC,p.isTop DESC,p.updateTime DESC ");
		query.setParameter(1, cateId);
		query.setParameter(2, paperId);
		query.setFirstResult(offset);//设置查询结果的开始记录数
		query.setMaxResults(limit);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Paper> getfPaperByPage(int limit,int offset) {
		Query query =  entityManager.createQuery("select p from Paper p where p.disabled = 0 and p.hasAudit = true ORDER BY p.hasAudit DESC,p.isTop DESC,p.updateTime DESC ");
		query.setFirstResult(offset);//设置查询结果的开始记录数
		query.setMaxResults(limit);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Paper> getfPaperByCategory(long cateId,int limit,int offset) {
		Query query =  entityManager.createQuery("select p from Paper p where p.disabled = 0 and p.hasAudit = true and p.channelId = ?1 ORDER BY p.hasAudit DESC,p.isTop DESC,p.updateTime DESC ");
		query.setParameter(1, cateId);
		query.setFirstResult(offset);//设置查询结果的开始记录数
		query.setMaxResults(limit);
		return query.getResultList();
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public int auditPaper(Long id,boolean hasAudit) {
		Query query = entityManager.createQuery("update Paper p set p.hasAudit = ?1,p.updateTime = CURRENT_TIMESTAMP(),p.auditTime = CURRENT_TIMESTAMP() where p.id = ?2");
		query.setParameter(1, hasAudit);
		query.setParameter(2, id);
		return query.executeUpdate();
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public int updateViewCount(Long id,Long count) {
		Query query = entityManager.createQuery("update Paper p set p.viewCount = ?1,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?2");
		query.setParameter(1, count);
		query.setParameter(2, id);
		return query.executeUpdate();
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public int updateTop(Long id,int isTop) {
		Query query = entityManager.createQuery("update Paper p set p.isTop = ?1,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?2");
		query.setParameter(1, isTop);
		query.setParameter(2, id);
		return query.executeUpdate();
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public int delete(Long id) {
		Query query = entityManager.createQuery("update Paper p set p.disabled = 1,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?1");
		query.setParameter(1, id);
		return query.executeUpdate();
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public int batchDelete(String ids) {
		String sqlString = "update Paper p set p.disabled = 1,p.updateTime = CURRENT_TIMESTAMP() where p.id in ("+ids+")";
		Query query = entityManager.createQuery(sqlString);
		return query.executeUpdate();
	}
	@Transactional
	public Paper save(Paper paper) {
		if (paper.getId() == null) {
			entityManager.persist(paper);
			return paper;
		} else {
			return entityManager.merge(paper);
		}		
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public int batchAudit(String ids) {
		String sqlString = "update Paper p set p.hasAudit = true,p.updateTime = CURRENT_TIMESTAMP() where p.id in ("+ids+")";
		Query query = entityManager.createQuery(sqlString);
		return query.executeUpdate();
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public int updateRecom(Long id,int isRecom) {
		Query query = entityManager.createQuery("update Paper p set p.isRecom = ?1,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?2");
		query.setParameter(1, isRecom);
		query.setParameter(2, id);
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Paper> getPaperByCondition(String string, int limit, int offset) {
		Query query =  entityManager.createQuery(string);
		query.setFirstResult(offset);//设置查询结果的开始记录数
		query.setMaxResults(limit);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public Long getPaperSizeByCondition(String string) {
		Query query =  entityManager.createQuery(string);
		return (Long)query.getSingleResult();
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public int deleteImg(Long id) {
		Query query = entityManager.createQuery("update Paper p set p.titleImg = null,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?1");
		query.setParameter(1, id);
		return query.executeUpdate();
	}
	
}
