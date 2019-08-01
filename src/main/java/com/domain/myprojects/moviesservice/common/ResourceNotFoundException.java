package com.domain.myprojects.moviesservice.common;

/**
 * Exception used  when a requested resource is not found
 */
public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String exception) {
		super(exception);
	}
}