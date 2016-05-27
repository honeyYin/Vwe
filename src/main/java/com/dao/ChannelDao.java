package com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Channel;

@Repository
public class ChannelDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	public Channel find(Long id) {
		return entityManager.find(Channel.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Channel> getAllCategory() {
		return entityManager.createQuery("select p from Channel p where p.disabled = 0 order by p.priority ASC,p.updateTime DESC").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Channel> getRootCategory(){
		return entityManager.createQuery("select p from Channel p where p.disabled = 0 and p.parentId =0 ORDER BY p.priority ASC").getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Channel> getCategoriesByParent(Long parentId){
		Query query = entityManager.createQuery("select p from Channel p where p.disabled = 0 and p.parentId = ?1");
		query.setParameter(1, parentId);
		return query.getResultList();
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public void delete(Long id) {
		Query query = entityManager.createQuery("update Channel p set p.disabled = 1,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?1");
		query.setParameter(1, id);
		query.executeUpdate();
	}
	
	@Transactional
	public Channel save(Channel cate) {
		if (cate.getId() == null) {
			entityManager.persist(cate);
			return cate;
		} else {
			return entityManager.merge(cate);
		}		
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public void deploy(long channelId) {
		Query query = entityManager.createQuery("update Channel p set p.isDeploy = 1,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?1");
		query.setParameter(1, channelId);
		query.executeUpdate();
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public void unDeploy(long id) {
		Query query = entityManager.createQuery("update Channel p set p.isDeploy = 0,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?1");
		query.setParameter(1, id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public Integer getPageSizeByChannel(Long channelId) {
		Query query = entityManager.createQuery("select p.pageSize from Channel p where p.disabled = 0 and p.id = ?1");
		query.setParameter(1, channelId);
		return query.getFirstResult();
	}
}
