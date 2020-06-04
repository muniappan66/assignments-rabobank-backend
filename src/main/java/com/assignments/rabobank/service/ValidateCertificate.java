package com.assignments.rabobank.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.assignments.rabobank.exceptions.PaymentException;
import com.assignments.rabobank.model.PaymentInitiationBody;

/**
 * The Interface ValidateCertificate.
 */
public interface ValidateCertificate {

	/**
	 * Request validation.
	 *
	 * @param reqDetails the req details
	 * @param headerMap  the header map
	 * @return the response entity
	 * @throws PaymentException 
	 */
	public ResponseEntity<?> requestValidation(PaymentInitiationBody reqDetails, Map<String, String> headerMap) ;

}
