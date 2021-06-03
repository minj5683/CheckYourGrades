package com.minj.checkYourGrades.dao;

import com.minj.checkYourGrades.model.Grade;
import com.minj.checkYourGrades.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository 
@Transactional
public class UserDao {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	public User getUserById(int id) {
		Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, id);

        return user;
	}
	
	@SuppressWarnings("unchecked")
	public User getUserByPassword(String password) {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery("from User where password=:password");
        query.setParameter("password", Integer.parseInt(password));
        
        //System.out.println("*****" + password);
        //System.out.println("*****" + query.getSingleResult().getUsername());

        try {
        	return query.getSingleResult();
        } catch(NoResultException e) {
        	return null;
        }
	}
	
	@SuppressWarnings("unchecked")
	public boolean isAdminExist() {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery("from User where username=:username");
        query.setParameter("username", "admin");
        
        try {
        	return query.getSingleResult() == null ? false : true;
        } catch(NoResultException e) {
        	return false;
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("from User");
        List<User> userList = query.getResultList();

        return userList;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getAllUsersJSON() {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("from User order by username");
        
        List<User> userList =  query.getResultList();
        JSONArray jsonArray = new JSONArray();
        
        for(User user : userList) {
        	jsonArray.add(user);
        }
        
        return jsonArray;
	}
	
	@SuppressWarnings("deprecation")
	public void deleteAllUsers() {
		Session session = sessionFactory.getCurrentSession();
		session.createQuery("delete from User").executeUpdate();
		session.createQuery("delete from Grade").executeUpdate();
		//session.createSQLQuery("alter table Grade AUTO_INCREMENT=1").executeUpdate();
		//session.createQuery("truncate table Grade").executeUpdate();
		session.flush();
	}
	
	public void addAdmin() {
		User user = new User();
		Grade grade = new Grade();
		grade.setTotal("");
		grade.setDetail("");

		user.setGrade(grade);
		user.setUsername("admin");
		user.setEnabled(true);
		user.setPassword(0000);
		user.setAuthority("ROLE_ADMIN");
		
		addUser(user);
	}
	
	public void addUser(User user) {
		Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
        session.flush();
	}

	public void deleteUser(User user) {
		Session session = sessionFactory.getCurrentSession();
        session.delete(user);
        session.flush();
	}

	public void updateUser(User user) {
		Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
        session.flush();
	}
}
