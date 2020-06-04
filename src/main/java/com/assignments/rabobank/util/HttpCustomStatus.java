package com.assignments.rabobank.util;

/**
 * The Enum HttpCustomStatus.
 */
public enum HttpCustomStatus {

	/** The unknown certificate. */
	UNKNOWN_CERTIFICATE(0, "UNKNOWN_CERTIFICATE"),
	/** The invalid signature. */
	INVALID_SIGNATURE(1, "INVALID_SIGNATURE"),

	/** The invalid request. */
	INVALID_REQUEST(2, "INVALID_REQUEST"),
	/** The limit exceeded. */
	LIMIT_EXCEEDED(3, "LIMIT_EXCEEDED"),
	/** The general error. */
	GENERAL_ERROR(4, "GENERAL_ERROR"),

	/** The payment accepted. */
	PAYMENT_ACCEPTED(5, "PAYMENT_ACCEPTED");

	/** The code. */
	private int code;

	/** The message. */
	private String message;

	/**
	 * Instantiates a new http custom status.
	 *
	 * @param code    the code
	 * @param message the message
	 */
	private HttpCustomStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the upload response.
	 *
	 * @param code the code
	 * @return the upload response
	 */
	public static HttpCustomStatus getUploadResponse(int code) {
		for (HttpCustomStatus response : HttpCustomStatus.values()) {
			if (response.code == code) {
				return response;
			}
		}

		throw new IllegalArgumentException("Unsupported UploadResponse code: " + code);
	}
}
