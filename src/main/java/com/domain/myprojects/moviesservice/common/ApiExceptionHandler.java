package com.domain.myprojects.moviesservice.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
		@ExceptionHandler(Exception.class)
		public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
			CustomeErrorMessage error = new CustomeErrorMessage("Server Error", ex);
			return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		@ExceptionHandler(ResourceNotFoundException.class)
		public final ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
			CustomeErrorMessage error = new CustomeErrorMessage("Record Not Found", ex);
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
		}
}