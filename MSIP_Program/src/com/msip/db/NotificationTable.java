package com.msip.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import com.msip.model.Notification;
import com.mysql.jdbc.PreparedStatement;

public class NotificationTable {

	private SimpleDateFormat formatter;
	private Date tomorrowsDate;

	/**
	 * Creates Notification Table if it doesn't exist.
	 */
	public NotificationTable() {
		try {
			PreparedStatement createTable = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("CREATE TABLE IF NOT EXISTS Notification(ID INT AUTO_INCREMENT, Notification VARCHAR(400), StartDate DATE NOT NULL, ExpirationDate DATE NOT NULL, PRIMARY KEY(ID))");
			createTable.executeUpdate();
			System.out.println("Successfully created Notification Table");

		} catch (SQLException e) {
			System.out.println("Failed to create Notification table" + "\n");

			e.printStackTrace();
		}
	}

	/**
	 * Adds new notification to the table.
	 * 
	 * @param notification
	 * @param startDate
	 * @param expirationDate
	 */
	public void addToNotificationTable(String notification, Date startDate,
			Date expirationDate) {
		 formatter = new SimpleDateFormat("yyyy-MM-dd");
		String startDateString = formatter.format(startDate);
		String expirationDateString = formatter.format(expirationDate);

		try {
			PreparedStatement addNotification = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("INSERT INTO Notification VALUE(NULL,'"
							+ notification + "',  '" + startDateString + "','"
							+ expirationDateString + "');");
			addNotification.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Returns an arraylist of active notifications.
	 * 
	 * @return
	 */
	public ArrayList<Notification> getAllNotification() {
		// get all valid notifications that are not expired and have started
		ArrayList<Notification> myNotif = new ArrayList<Notification>();
		Notification notifications = null;
		try {
			PreparedStatement notif = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("SELECT * FROM Notification;");
			ResultSet rs = notif.executeQuery();
			while (rs.next()) {
				removeExpiredNotification();
				notifications = new Notification(rs.getString("Notification"), rs.getDate("StartDate"), rs.getDate("ExpirationDate"));
				myNotif.add(notifications);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return myNotif;
	}

	/**
	 * removes expired notification.
	 * 
	 * @param experationDate
	 */
	public void removeNotification(String notification) {
		try {
			PreparedStatement delete = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("DELETE FROM Notification WHERE  Notification='"
							+ notification + "';");
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void removeFromNotificationTable(Date expirationDate) {
		try {
			PreparedStatement delete = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("DELETE FROM Notification WHERE ExpirationDate ='"
							+ expirationDate + "';");
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Removes all expired notifications.
	 */
	public void removeExpiredNotification() {

		Date expiredDate = null;
		 tomorrowsDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = ZonedDateTime.now().plusDays(1).format(
				DateTimeFormatter.ISO_LOCAL_DATE);
		try {
			tomorrowsDate = formatter.parse(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			PreparedStatement getExpiredDate = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("SELECT ExpirationDate FROM Notification;");

			ResultSet rs = getExpiredDate.executeQuery();
			while (rs.next()) {
				expiredDate = rs.getDate("ExpirationDate");

				if (expiredDate.compareTo(tomorrowsDate) > 0) {
					System.out.println("Successfully removed: " + new Date());
					removeFromNotificationTable(expiredDate);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clears the notification Table.
	 */
	public void removeAll() {
		try {
			PreparedStatement removeAll = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("DELETE FROM Notification;");
			removeAll.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
