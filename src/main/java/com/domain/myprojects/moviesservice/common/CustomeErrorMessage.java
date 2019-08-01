package com.domain.myprojects.moviesservice.common;

public class CustomeErrorMessage {

	private String errorMessage;
	private Throwable e;

	public CustomeErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public CustomeErrorMessage(String errorMessage, Exception e) {
		this.errorMessage = errorMessage;
		this.e = e;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public Throwable getCause() {
		return e.getCause();
	}
}