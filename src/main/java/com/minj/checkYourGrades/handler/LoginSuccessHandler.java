package com.minj.checkYourGrades.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		//System.out.println("login success! " + authentication.getName()/* + ", " + authentication.toString() */);
		logger.info("** Login success! Username: {}, License Num: {} **", request.getParameter("password"), request.getParameter("username"));
		
			if((request.getParameter("password")).equals("admin")) {
				logger.info("Send admin page");
				response.sendRedirect("admin");
				
			} else {
				logger.info("Send result page");
				HttpSession session = request.getSession();
				
				int num = Integer.parseInt(request.getParameter("username"));
				session.setAttribute("licenseNum", num);
				
				response.sendRedirect("result");
			}
	}

}
