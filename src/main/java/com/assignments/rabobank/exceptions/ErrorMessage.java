package com.assignments.rabobank.exceptions;

public class ErrorMessage {
	private String reasonCode;
	private String reason;
	private  String status;
	
	public ErrorMessage(String status, String reason, String reasonCode) {
		super();
		this.reasonCode = reasonCode;
		this.reason = reason;
		this.status = status;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
