package com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entity.User;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public User find(Long id) {
		return entityManager.find(User.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUser() {
		return entityManager.createQuery("select p from User p").getResultList();
	}
	
	@Transactional
	public User save(User person) {
		if (person.getId() == null) {
			entityManager.persist(person);
			return person;
		} else {
			return entityManager.merge(person);
		}		
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public int updatePwd(Long userId,String newPwd) {
		Query query = entityManager.createQuery("update User p set p.password = ?1,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?2");
		query.setParameter(1, newPwd);
		query.setParameter(2, userId);
		return query.executeUpdate();
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public int updateDisabled(Long id,int disabled) {
		Query query = entityManager.createQuery("update User p set p.disabled = ?1,p.updateTime = CURRENT_TIMESTAMP() where p.id = ?2");
		query.setParameter(1, disabled);
		query.setParameter(2, id);
		return query.executeUpdate();
	}
	@SuppressWarnings("unchecked")
	public List<User> findByLoginName(String loginName) {
		Query query =  entityManager.createQuery("select p from User p where p.loginName = ?1");
		query.setParameter(1, loginName);
		return query.getResultList();
	}
	
}
