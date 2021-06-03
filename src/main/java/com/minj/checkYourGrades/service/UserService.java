package com.minj.checkYourGrades.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minj.checkYourGrades.dao.UserDao;
import com.minj.checkYourGrades.model.User;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	public void addUser(User user) {
		userDao.addUser(user);
	}
	
	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}
	
	public void updateUser(User user) {
		userDao.addUser(user);
	}
	
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}
	
	public User getUserByPassword(String password) {
		return userDao.getUserByPassword(password);
	}
	
	public boolean isAdminExist() {
		return userDao.isAdminExist();
	}
	
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
	
	public JSONArray getAllUsersJSON() {
		return userDao.getAllUsersJSON();
	}
	
	public void deleteAllUsers() {
		userDao.deleteAllUsers();
	}
	
	public void addAdmin() {
		userDao.addAdmin();
	}
}
