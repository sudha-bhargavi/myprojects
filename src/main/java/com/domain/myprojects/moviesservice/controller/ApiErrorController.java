package com.domain.myprojects.moviesservice.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiErrorController implements ErrorController {

	private static final String PATH = "/error";

	@RequestMapping(value = PATH)
	public String error() {
		return ".Something is wrong. Contact Admin For more Information";
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
}