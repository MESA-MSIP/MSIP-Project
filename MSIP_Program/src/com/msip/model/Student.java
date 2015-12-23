package com.msip.model;

public class Student extends Person {

	private String major;

	/**
	 * @param firstName
	 * @param lastName
	 * @param kNumber
	 * @param major
	 */
	public Student(String firstName, String lastName, int kNumber, String major) {
		super(firstName, lastName, kNumber);
		this.setMajor(major);
	}

	/**
	 * Gets Major of student
	 * 
	 * @return major of student
	 */
	public String getMajor() {
		return major;
	}

	/**
	 * @param major
	 */
	public void setMajor(String major) {
		this.major = major;
	}

	enum ParcipitationState {
		INACTIVE, ACTIVE
	}

}
