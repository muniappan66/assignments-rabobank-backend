package com.assignments.rabobank.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.assignments.rabobank.exceptions.ErrorMessage;
import com.assignments.rabobank.exceptions.PaymentException;
import com.assignments.rabobank.util.ApplicationConstant;
@EnableWebMvc
@ControllerAdvice
public class PaymentExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(PaymentException.class)
	public final ResponseEntity<ErrorMessage> someThingWrong(PaymentException e) {
		ErrorMessage errorMessage =null;
		if(e.getMessage().equalsIgnoreCase("certificate-exception")) {
		 errorMessage = new ErrorMessage(ApplicationConstant.REJECTED_STATUS, "Problem with Certifacte data", "certificate-exception");
		 return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		if(e.getMessage().equalsIgnoreCase("INVALID_REQUEST")) {
			 errorMessage = new ErrorMessage(ApplicationConstant.REJECTED_STATUS, "Request details are invalid", "INVALID_REQUEST");
			 return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}

		if(e.getMessage().equalsIgnoreCase("other_validations")) {
			 errorMessage = new ErrorMessage(ApplicationConstant.REJECTED_STATUS, "Problem with other validations", "other_validations");
			 return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		if(e.getMessage().equalsIgnoreCase("INVALID_SIGNATURE")) {
			 errorMessage = new ErrorMessage(ApplicationConstant.REJECTED_STATUS, "Problem with  signature input", "INVALID_SIGNATURE");
			 return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		if(e.getMessage().equalsIgnoreCase("UNKNOWN_CERTIFICATE")) {
			 errorMessage = new ErrorMessage(ApplicationConstant.REJECTED_STATUS, "Problem with  certificate input", "UNKNOWN_CERTIFICATE");
			 return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		if(e.getMessage().equalsIgnoreCase("signature-exception")) {
			 errorMessage = new ErrorMessage(ApplicationConstant.REJECTED_STATUS, "Exception in  signature validation", "signature-exception");
			 return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
