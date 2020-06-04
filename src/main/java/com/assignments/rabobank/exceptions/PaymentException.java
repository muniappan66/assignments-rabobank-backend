package com.assignments.rabobank.exceptions;

public class PaymentException extends RuntimeException {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private String exceptionMessage;

	public PaymentException(String message) {
		super();
		this.exceptionMessage = message;
	}
    @Override
	public String getMessage() {
		return exceptionMessage;
	}

	public void setMessage(String message) {
		this.exceptionMessage = message;
	}
}
