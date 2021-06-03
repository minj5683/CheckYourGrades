package com.minj.checkYourGrades.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.minj.checkYourGrades.dao.UserDao;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
    private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String password) throws UsernameNotFoundException {
		
		com.minj.checkYourGrades.model.User user = userDao.getUserByPassword(password);
		//System.out.println("-----" + user.getUsername() + " | " + user.getPassword() + " | " + user.getAuthority() + "-----");
		
		if(user == null){
	        throw new UsernameNotFoundException("User not authorized licenseNum: " + password);
	    }
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getAuthority())); 
		//UserDetails userDetails = new User(user.getUsername(), password, authorities); 
		UserDetails userDetails = new User(password, user.getUsername(), user.isEnabled(), true, true, true, authorities);
		//System.out.println(userDetails.getUsername() + " | " + userDetails.getPassword() + "(" + userDetails.getPassword().getClass().getName() + ") | " + userDetails.getAuthorities().toString() + " | " + userDetails.isEnabled());
        
		return userDetails;
	}

}
