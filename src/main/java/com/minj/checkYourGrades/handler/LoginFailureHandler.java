package com.minj.checkYourGrades.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {
	private static final Logger logger = LoggerFactory.getLogger(LoginFailureHandler.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

        logger.error("** Login failure! Username: {}, License Num: {}, Error: {} ** ", request.getParameter("password"), request.getParameter("username"), exception.getMessage());
		
		response.sendRedirect("login?error=1");
        //request.getRequestDispatcher("login?error=1").forward(request, response);
	}

}
