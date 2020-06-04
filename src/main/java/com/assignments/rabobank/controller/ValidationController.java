package com.assignments.rabobank.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assignments.rabobank.exceptions.PaymentException;
import com.assignments.rabobank.model.PaymentInitiationBody;
import com.assignments.rabobank.service.ValidateCertificate;
import com.assignments.rabobank.util.ApplicationConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * The Class ValidationController.
 */
@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
@RequestMapping("/v1.0.0")
@Api(value = "API Payment Initiation API")
public class ValidationController {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(ValidationController.class);

	/** The validate certificate. */
	@Autowired
	private ValidateCertificate validateCertificate;

	/**
	 * Before calling third party.
	 *
	 * @param reqDetails  the req details
	 * @param requestId   the request id
	 * @param signature   the signature
	 * @param certificate the certificate
	 * @return the response entity
	 * @throws PaymentException 
	 */
	@RequestMapping(value = "/initiate-payment", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "initiatePayment validation")
	public ResponseEntity<?> initiatePayment(@RequestBody PaymentInitiationBody reqDetails,
			@RequestHeader(name = ApplicationConstant.X_REQUEST_ID, required = true) String requestId,
			@RequestHeader(name = ApplicationConstant.SIGNATURE, required = true) String signature,
			@RequestHeader(name = ApplicationConstant.SIGNATURE_CERTIFICATE, required = true) String certificate)  {
		ResponseEntity<?> response = null;
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put(ApplicationConstant.X_REQUEST_ID, requestId);
		headerMap.put(ApplicationConstant.SIGNATURE, signature);
		headerMap.put(ApplicationConstant.SIGNATURE_CERTIFICATE, certificate);
		response = validateCertificate.requestValidation(reqDetails, headerMap);
		JSONObject obj = new JSONObject();
		HttpHeaders httpHeaders = new HttpHeaders();

		if (response != null) {
			return response;
		} else {
			obj.put(ApplicationConstant.STATUS, "Rejected");
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(httpHeaders)
					.body(obj.toString());
			return response;
		}

	}
}
