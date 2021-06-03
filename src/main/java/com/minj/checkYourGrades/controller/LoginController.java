package com.minj.checkYourGrades.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error, 
			@RequestParam(value="logout", required=false) String logout, Model model) {
		
		if(error!=null) {
			model.addAttribute("errorMsg", "성명과 면허번호가 일치하지 않습니다. <br>다시 입력해주세요.");
			logger.info("Login failed");
		} 
		
		/*
		 * if(logout!=null) {
		 * model.addAttribute("logoutMsg", "You have been logged out seccessfully"); }
		 */
		
		return "login";
	}
	
	@RequestMapping("/error/accessDenied")
	public String accessDenied() {
		logger.error("Access denied");
		return "error/accessDenied";
	}
}
