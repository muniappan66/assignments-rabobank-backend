package com.assignments.rabobank.service;

/**
 * The Interface SumCalculation.
 */
@FunctionalInterface
public interface SumCalculation {

	/**
	 * Gets the sum calculation.
	 *
	 * @param debtorIBAN the debtor IBAN
	 * @return the sum calculation
	 */
	public int getSumCalculation(String debtorIBAN);

}
