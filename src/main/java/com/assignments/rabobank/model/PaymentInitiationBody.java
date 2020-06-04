package com.assignments.rabobank.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * The Class PaymentInitiationBody.
 */
@ApiModel(description = "All details about the PaymentInitiationBody")
public class PaymentInitiationBody implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The debtor IBAN. */
	@ApiModelProperty(notes = "The debtor IBAN.")
	private String debtorIBAN;

	/** The creditor IBAN. */
	@ApiModelProperty(notes = " The creditor IBAN.")
	private String creditorIBAN;

	/** The amount. */
	@ApiModelProperty(notes = "The amount.")
	private String amount;

	/** The currency. */
	@ApiModelProperty(notes = " The currency.")
	private String currency;

	/** The endend to end id. */
	@ApiModelProperty(notes = "The endend to end id.")
	private String endendToEndId;

	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency.
	 *
	 * @param currency the new currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * Gets the endend to end id.
	 *
	 * @return the endend to end id
	 */
	public String getEndendToEndId() {
		return endendToEndId;
	}

	/**
	 * Sets the endend to end id.
	 *
	 * @param endendToEndId the new endend to end id
	 */
	public void setEndendToEndId(String endendToEndId) {
		this.endendToEndId = endendToEndId;
	}

	/**
	 * Gets the debtor IBAN.
	 *
	 * @return the debtor IBAN
	 */
	public String getDebtorIBAN() {
		return debtorIBAN;
	}

	/**
	 * Sets the debtor IBAN.
	 *
	 * @param debtorIBAN the new debtor IBAN
	 */
	public void setDebtorIBAN(String debtorIBAN) {
		this.debtorIBAN = debtorIBAN;
	}

	/**
	 * Gets the creditor IBAN.
	 *
	 * @return the creditor IBAN
	 */
	public String getCreditorIBAN() {
		return creditorIBAN;
	}

	/**
	 * Sets the creditor IBAN.
	 *
	 * @param creditorIBAN the new creditor IBAN
	 */
	public void setCreditorIBAN(String creditorIBAN) {
		this.creditorIBAN = creditorIBAN;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

}
