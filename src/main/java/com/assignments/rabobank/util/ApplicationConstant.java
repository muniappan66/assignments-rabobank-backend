package com.assignments.rabobank.util;

/**
 * The Class ApplicationConstant.
 */
public final class ApplicationConstant {
	
	private ApplicationConstant() {
		
	}

	/** The Constant X509. */
	public static final String X509 = "X.509";

	/** The Constant RSA. */
	public static final String RSA = "RSA";

	/** The Constant SHA256RSA. */
	public static final String SHA256RSA = "SHA256withRSA";

	/** The Constant SandboxTPP. */
	public static final String SANDBOX_TPP = "Sandbox-TPP";

	/** The Constant STATUS. */
	public static final String STATUS = "status";

	/** The Constant REASON. */
	public static final String REASON = "reason";

	/** The Constant REASONCODE. */
	public static final String REASONCODE = "reasonCode";

	/** The Constant PAYMENT_ID. */
	public static final String PAYMENT_ID = "paymentId";

	/** The Constant SIGNATURE_VERIFIED. */
	public static final String SIGNATURE_VERIFIED = "SignatureVerified";


	/** The Constant SIGNATURE_VERIFIED. */
	public static final String SIGNATURE_FAILED = "SignatureFailed";
	
	/** The Constant SIGNATURE_CERTIFICATE. */
	public static final String SIGNATURE_CERTIFICATE = "Signature-Certificate";

	/** The Constant SIGNATURE. */
	public static final String SIGNATURE = "Signature";

	/** The Constant X_REQUEST_ID. */
	public static final String X_REQUEST_ID = "X-Request-Id";

	/** The Constant CURRENCY_REGEX. */
	public static final String CURRENCY_REGEX = "[A-Z]{3}";

	/** The Constant AMOUNT_REGEX. */
	public static final String AMOUNT_REGEX = "-?[0-9]+(\\.[0-9]{1,3})?";
	
	public static final  String REJECTED_STATUS="Rejected";
	
	public static final  String SIGNATURE_EXCEPTION="signature-exception";

}
