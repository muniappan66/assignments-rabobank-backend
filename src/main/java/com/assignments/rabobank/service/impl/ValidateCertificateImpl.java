package com.assignments.rabobank.service.impl;

import java.io.ByteArrayInputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.assignments.rabobank.exceptions.PaymentException;
import com.assignments.rabobank.model.PaymentInitiationBody;
import com.assignments.rabobank.service.SumCalculation;
import com.assignments.rabobank.service.ValidateCertificate;
import com.assignments.rabobank.util.ApplicationConstant;
import com.assignments.rabobank.util.HttpCustomStatus;

/**
 * The Class ValidateCertificateImpl.
 */
@Service
public class ValidateCertificateImpl implements ValidateCertificate {
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(ValidateCertificateImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.assignments.rabobank.service.ValidateCertificate#requestValidation(com.
	 * assignments.rabobank.model.PaymentInitiationBody, java.util.HashMap)
	 */
	@Override
	public ResponseEntity<?> requestValidation(PaymentInitiationBody paymentDetails, Map<String, String> headerMap) {
		logger.info("method requestValidation started");
		JSONObject obj = new JSONObject();
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<?> response = null;
		HttpCustomStatus httpEnumRes = null;
		if (StringUtils.isEmpty(paymentDetails.getDebtorIBAN())
				|| StringUtils.isEmpty(paymentDetails.getCreditorIBAN())) {
			httpEnumRes = HttpCustomStatus.getUploadResponse(2);
			obj.put(ApplicationConstant.STATUS, ApplicationConstant.REJECTED_STATUS);
			obj.put(ApplicationConstant.REASON, "Request details validation failed");
			obj.put(ApplicationConstant.REASONCODE, httpEnumRes.getMessage());
			response = ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(obj.toString());
			return response;
		}
		if (!StringUtils.isEmpty(paymentDetails.getAmount()) && !StringUtils.isEmpty(paymentDetails.getDebtorIBAN())) {
			String amountRegex = ApplicationConstant.AMOUNT_REGEX;
			// Create a Pattern object for amount
			Pattern amountPattern = Pattern.compile(amountRegex);
			// Now create matcher object.
			Matcher amountMatch = amountPattern.matcher(paymentDetails.getAmount());

			String currencyRegex = ApplicationConstant.CURRENCY_REGEX;
			// Create a Pattern object for amount
			Pattern currencyPattern = Pattern.compile(currencyRegex);
			// Now create matcher object.
			Matcher currencyMatch = currencyPattern.matcher(paymentDetails.getCurrency());

			if (amountMatch.matches() && currencyMatch.matches()) {
				response = otherValidations(paymentDetails, headerMap);

			} else {
				httpEnumRes = HttpCustomStatus.getUploadResponse(2);
				obj.put(ApplicationConstant.STATUS, ApplicationConstant.REJECTED_STATUS);
				obj.put(ApplicationConstant.REASON, "Please enter valid Request details");
				obj.put(ApplicationConstant.REASONCODE, httpEnumRes.getMessage());
				response = ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(obj.toString());
			}
		} else {
			httpEnumRes = HttpCustomStatus.getUploadResponse(2);
			obj.put(ApplicationConstant.STATUS, ApplicationConstant.REJECTED_STATUS);
			obj.put(ApplicationConstant.REASON, "Please enter valid Request details");
			obj.put(ApplicationConstant.REASONCODE, httpEnumRes.getMessage());
			response = ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(obj.toString());
			return response;
		}
		logger.info("method requestValidation ends");
		return response;
	}

	/**
	 * Other validation.
	 *
	 * @param reqDetails the req details
	 * @param headerMap  the header map
	 * @return the response entity
	 */
	public ResponseEntity<?> otherValidations(PaymentInitiationBody reqDetails, Map<String, String> headerMap)

	{

		JSONObject obj = new JSONObject();
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<?> response = null;
		logger.info("inside method otherValidation");

		// validation 1 :Amount limit exceeded:
		// sum of DebtorAccountIBAN without alphabets
		double amount = Double.parseDouble(reqDetails.getAmount());
		String debtorNumber = reqDetails.getDebtorIBAN();
		int debtorLength = debtorNumber.length();
		int sumValue = 0;

		SumCalculation calObj = (singleChar) -> {
			int sumOfDigit = 0;
			for (char iChar : singleChar.toCharArray()) {
				if (Character.isDigit(iChar)) {
					sumOfDigit = sumOfDigit + Integer.parseInt(String.valueOf(iChar));
				}
			}

			return sumOfDigit;
		};
		sumValue = calObj.getSumCalculation(debtorNumber);

		if (amount > 0 && (sumValue % debtorLength == 0)) {
			// 2. validate certificate
			String signature = headerMap.get(ApplicationConstant.SIGNATURE);
			String certicificate = headerMap.get(ApplicationConstant.SIGNATURE_CERTIFICATE);
			response = verifyTheCertificate(certicificate, signature);
		} else {
			HttpCustomStatus httpEnumRes = HttpCustomStatus.getUploadResponse(3);
			obj.put(ApplicationConstant.STATUS, ApplicationConstant.REJECTED_STATUS);
			obj.put(ApplicationConstant.REASON, "Limit exceeded validation failed");
			obj.put(ApplicationConstant.REASONCODE, httpEnumRes.getMessage());
			response = ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(obj.toString());
		}

		logger.info("inside method otherValidation ends");
		return response;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.assignments.rabobank.service.ValidateCertificate#verifytheCertificate(
	 * java.lang.String, java.lang.String)
	 */
	public ResponseEntity<?> verifyTheCertificate(String certificate, String signatureHeader) {
		logger.info("inside method verifytheCertificate starts");
		ResponseEntity<?> response = null;
		if (!StringUtils.isEmpty(certificate)) {
			CertificateFactory certFactory = null;
			X509Certificate cert = null;
			byte[] encodedCert = null;
			ByteArrayInputStream inputStream = null;
			JSONObject obj = new JSONObject();
			HttpHeaders httpHeaders = new HttpHeaders();
			HttpCustomStatus httpEnumRes = null;
			try {
				encodedCert = Base64.getDecoder().decode(certificate);
				inputStream = new ByteArrayInputStream(encodedCert);
				// Certificate validation
				certFactory = CertificateFactory.getInstance(ApplicationConstant.X509);
				cert = (X509Certificate) certFactory.generateCertificate(inputStream);
				String subject = cert.getIssuerDN().getName();
				if (!StringUtils.isEmpty(subject.contains("Sandbox-TPP"))) {
					// call Signature validation X509
					String signatureValid = validateSignature(cert, signatureHeader);
					if (signatureValid.equals(ApplicationConstant.SIGNATURE_VERIFIED)) {
						final String uuid = UUID.randomUUID().toString();
						httpEnumRes = HttpCustomStatus.getUploadResponse(5);
						obj.put(ApplicationConstant.PAYMENT_ID, uuid);
						obj.put(ApplicationConstant.STATUS, "Accepted");
						obj.put(ApplicationConstant.REASONCODE, httpEnumRes.getMessage());
						response = ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(obj.toString());
					} else {
						httpEnumRes = HttpCustomStatus.getUploadResponse(1);
						obj.put(ApplicationConstant.STATUS, ApplicationConstant.REJECTED_STATUS);
						obj.put(ApplicationConstant.REASON, "Invalid Signature");
						obj.put(ApplicationConstant.REASONCODE, httpEnumRes.getMessage());
						response = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).headers(httpHeaders)
								.body(obj.toString());
					}
				} else {
					httpEnumRes = HttpCustomStatus.getUploadResponse(0);
					obj.put(ApplicationConstant.STATUS, ApplicationConstant.REJECTED_STATUS);
					obj.put(ApplicationConstant.REASON, "Unkown Certificate");
					obj.put(ApplicationConstant.REASONCODE, httpEnumRes.getMessage());
					response = ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(obj.toString());
				}

			} catch (CertificateException e) {
				logger.info("Exception in verifytheCertificate" + e);
				throw new PaymentException("certificate-exception");

			}
		} else {
			throw new PaymentException("UNKNOWN_CERTIFICATE");
		}
		logger.info("inside method verifytheCertificate ends");
		return response;
	}

	/**
	 * Validate signature.
	 *
	 * @param cert            the cert
	 * @param signatureHeader the signature header
	 * @return the string
	 * @throws PaymentException
	 */
	private String validateSignature(X509Certificate cert, String signatureHeader) {
		String signResponse = "";
		if (!StringUtils.isEmpty(signatureHeader)) {
			try {
				logger.info("inside method validateSignature starts");
				// validate signature - SHA256withRSA
				KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ApplicationConstant.RSA);
				keyPairGenerator.initialize(2048); // KeySize
				KeyPair keyPair = keyPairGenerator.generateKeyPair();
				PrivateKey privateKey = keyPair.getPrivate();
				PublicKey publicKey = keyPair.getPublic();
				byte[] data = signatureHeader.getBytes();
				Signature signature = Signature.getInstance(ApplicationConstant.SHA256RSA);
				signature.initSign(privateKey);
				signature.update(data);
				byte[] signedCertData = cert.getSignature();
				signature.initVerify(publicKey);

				signature.update(data);
				if (signature.verify(signedCertData)) {
					signResponse = ApplicationConstant.SIGNATURE_VERIFIED;
				} else {
					signResponse = ApplicationConstant.SIGNATURE_FAILED;
					// signResponse = ApplicationConstant.SIGNATURE_VERIFIED;

				}

			} catch (SignatureException e) {
				logger.info("SignatureException inside method validateSignature ends" + e);
				throw new PaymentException(ApplicationConstant.SIGNATURE_EXCEPTION);
			} catch (NoSuchAlgorithmException e) {
				logger.info("NoSuchAlgorithmException inside method validateSignature ends" + e);
				throw new PaymentException(ApplicationConstant.SIGNATURE_EXCEPTION);
			} catch (InvalidKeyException e) {
				logger.info("InvalidKeyException inside method validateSignature ends" + e);
				throw new PaymentException(ApplicationConstant.SIGNATURE_EXCEPTION);
			}
			logger.info("inside method validateSignature ends");
		} else {
			throw new PaymentException("INVALID_SIGNATURE");
		}
		return signResponse;
	}

}
