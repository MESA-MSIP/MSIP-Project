package com.msip.model;

public class Person {

	private String firstName = null;
	private String lastName = null;
	private int kNumber = 0;
	
	public Person(String firstName, String lastName, int kNumber) {
		setFirstName(firstName);
		setLastName(lastName);
		setkNumber(kNumber);
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
	 * @return the lastName
	 */
	public String getFullName() {
		return firstName + " " + lastName;
	}
	public String getLastNameFirstName(){
		return lastName + " " + firstName;
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
}
