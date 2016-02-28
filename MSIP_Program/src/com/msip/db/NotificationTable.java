package com.msip.db;

import java.sql.SQLException;
import java.util.Date;
import com.mysql.jdbc.PreparedStatement;

public class NotificationTable {
	
	/**
	 * Creates Notification Table if it doesn't exist.
	 */
	public NotificationTable() {
		try {
			PreparedStatement createTable = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("CREATE TABLE IF NOT EXISTS Notification(ID INT AUTO_INCREMENT, Notification VARCHAR(400), StartDate DATE NOT NULL, ExperationDate DATE NOT NULL, PRIMARY KEY(ID)");
			createTable.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds new notification to the table.
	 * @param notification
	 * @param startDate
	 * @param experationDate
	 */
	public void addToNotificationTable(String notification, Date startDate, Date experationDate) {
		try {
			PreparedStatement addNotification = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("INSERT INTO Notification VALUE(NULL,'" + notification + "',  '" + startDate + "','" + experationDate + "');");
			addNotification.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * removes expired notification.
	 * @param experationDate
	 */
	public void removeFromNotificationTable(Date experationDate) {
		try {
			PreparedStatement delete = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("DELETE FROM Notification WHERE ExperationDate ='"
							+ experationDate + "';");
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
