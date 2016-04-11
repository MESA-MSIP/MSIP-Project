package com.msip.model;

import java.util.Date;

public class Notification {
	private String notification;
	private Date startDate;
	private Date expirationDate;
	
	/**
	 * 
	 * @param notification
	 * @param startDate
	 * @param expirationDate
	 */
	public Notification(String notification, Date startDate, Date expirationDate){
		setNotification(notification);
		setStartDate(startDate);
		setExpirationDate(expirationDate);
	}

	/**
	 * @return notification String
	 */
	public String getNotification() {
		return notification;
	}

	/**
	 * @param notification
	 */
	public void setNotification(String notification) {
		this.notification = notification;
	}

	/**
	 * @return start date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return expiration Date.
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	

}
