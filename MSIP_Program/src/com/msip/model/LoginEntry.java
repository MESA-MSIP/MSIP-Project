package com.msip.model;

import java.sql.Date;

public class LoginEntry {
	
	private Date date = null;
	private int kNumber = 0;
	
	public LoginEntry(Date date, int KNumber) {
		this.setDate(date);
		this.setkNumber(KNumber);
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
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}
