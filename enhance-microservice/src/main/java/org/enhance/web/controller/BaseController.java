package org.enhance.web.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.enhance.common.util.InternalAssertion;

public abstract class BaseController {

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	protected ServletContext getServletContext() {
		return request.getSession().getServletContext();
	}

	protected String redirect(String path) {
		InternalAssertion.notEmpty(path, "redirect path cannot be empty.");
		path = path.trim();
		return "redirect:" + path;
	}
}
