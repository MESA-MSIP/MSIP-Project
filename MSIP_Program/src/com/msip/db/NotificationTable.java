package com.msip.db;

import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class NotificationTable {
	public NotificationTable() {
		try {
			PreparedStatement createTable = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("CREATE TABLE IF NOT EXISTS SurvayTable(ID INT AUTO_INCREMENT, Notification VARCHAR(400), StartDate DATE NOT NULL, ExperationDate DATE NOT NULL, PRIMARY KEY(ID)");
			createTable.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addToNotificationTable() {

	}

	public void removeFromNotificationTable() {

	}

}
