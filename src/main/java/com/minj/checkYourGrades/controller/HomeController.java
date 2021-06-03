package com.minj.checkYourGrades.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.minj.checkYourGrades.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		//trace --> debug --> info --> warn --> error
		
		if(!userService.isAdminExist()) {	
			logger.info("Admin does not exist. Add admin.");
			userService.addAdmin();
		} else {
			logger.info("Admin exist true");
		}
		
		logger.info("Request URL: {}, Client IP: {}", request.getRequestURL().toString(), request.getRemoteAddr());
		
		return "home";
	}
	
	
}
