package com.minj.checkYourGrades.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.minj.checkYourGrades.model.User;
import com.minj.checkYourGrades.service.UserService;

@Controller
public class ResultController {
	
	private static final Logger logger = LoggerFactory.getLogger(ResultController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/result")
	public String getResult(Model model, HttpServletRequest request) {
		
		/*if(request.getSession().getAttribute("licenseNum") == null) {
			System.out.println("**session destroyed**");
			return "home";
			
		} else {*/
		try {
			int id = (int) request.getSession().getAttribute("licenseNum");
			
			User user = userService.getUserById(id);
			user.setChecked(true);
			userService.updateUser(user);
			
			model.addAttribute("user", user);
			logger.info("** Load result page! Username: {}, License Num: {} **", user.getUsername(), user.getPassword());
			
			return "result";
		//}
		} catch(NullPointerException e) {
			logger.error("Session destroyed | {}", e.getMessage());
			return "logout";
		}
	}
}
