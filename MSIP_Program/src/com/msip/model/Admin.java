package com.msip.model;

public class Admin extends Person {

	private String pHash;

	public Admin(String firstName, String lastName, int kNumber, String pHash) {
		super(firstName, lastName, kNumber);
		this.setpHash(pHash);
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
