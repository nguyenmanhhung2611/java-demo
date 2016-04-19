package com.tctav.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tctav.demo.model.entities.User;

public class HandleInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		User userinfo = (User) request.getSession().getAttribute("user");
		// check if it is in login page => prevent redirect
		if (request.getServletPath().equals("/login")
				|| request.getServletPath().equals("/checklogin")) {
			return true;
		}
		// check the info of the user is login
		if (userinfo == null || userinfo.getUsername() == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		return true;
	}
}
