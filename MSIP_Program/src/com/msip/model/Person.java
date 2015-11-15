package com.msip.model;

public class Person {

	private String firstName = null;
	private String lastName = null;
	private int kNumber = 0;
	private String pHash = null;
	
	public Person(String firstName, String lastName, int kNumber, String pHash) {
		setFirstName(firstName);
		setLastName(lastName);
		setkNumber(kNumber);
		setpHash(pHash);
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the kNumber
	 */
	public int getkNumber() {
		return kNumber;
	}

	/**
	 * @param kNumber the kNumber to set
	 */
	public void setkNumber(int kNumber) {
		this.kNumber = kNumber;
	}

	/**
	 * @return the pHash
	 */
	public String getpHash() {
		return pHash;
	}

	/**
	 * @param pHash the pHash to set
	 */
	public void setpHash(String pHash) {
		this.pHash = pHash;
	}

}
