package com.msip.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import com.msip.model.LoginEntry;

/**
 * @author Fernando
 *
 */
public class LoginTable {

	/**
	 * Creates the LoginTable if it doesn't exist.
	 */
	public LoginTable() {
		PreparedStatement createLoginTable;
		try {
			createLoginTable = DBConnector.myConnection
					.prepareStatement("CREATE TABLE IF NOT EXISTS Login(ID INT NOT NULL AUTO_INCREMENT, "
							+ "Knumber INT NOT NULL, "
							+ "DateTime DATETIME NOT NULL, PRIMARY KEY(ID))");
			createLoginTable.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds student/admin to login table.
	 * 
	 * @param Knumber
	 */
	public void add(int Knumber) throws SQLException {
		PreparedStatement add = DBConnector.myConnection
				.prepareStatement("INSERT INTO Login VALUE(NULL, '" + Knumber
						+ "', NOW());");
		add.executeUpdate();
	}

	/**
	 * Removes student/admin from login table
	 */
	public void remove(int Knumber) {
		try {
			PreparedStatement delete = DBConnector.myConnection
					.prepareStatement("DELETE FROM Login WHERE Knumber='"
							+ Knumber + "';");
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes all rows from Admin table.
	 */
	public void deleteAll() {
		try {
			PreparedStatement deletAll = DBConnector.myConnection
					.prepareStatement("DELETE FROM Login");
			deletAll.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the current info of a Student/Admin logged in to the loginTable.
	 * 
	 * @param Knumber
	 * @return person
	 */
	public LoginEntry getInfo(int Knumber) {
		LoginEntry loginEntry = null;
		try {

			PreparedStatement studentInfo = DBConnector.myConnection
					.prepareStatement("SELECT * FROM Login WHERE Knumber='"
							+ Knumber + "';");
			ResultSet rs = studentInfo.executeQuery();

			while (rs.next()) {
				Date date = rs.getTimestamp("DateTime");
				loginEntry = new LoginEntry(date, Integer.parseInt(rs
						.getString("Knumber")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loginEntry;

	}

	/**
	 * Gets login entry for specific student or all students
	 * 
	 * @param Knumber
	 * @return list of dates.
	 */
	public ArrayList<Date> getLoginEntry(int Knumber) {
		ArrayList<Date> dateEntry = new ArrayList<Date>();

		try {
			if (Knumber != 0) {// When Knumber is null, we will select all
								// dates and add it to the date arraylist.
				PreparedStatement entries = DBConnector.myConnection
						.prepareStatement("SELECT DateTime FROM Login WHERE Knumber='"
								+ Knumber + "';");
				ResultSet rs = entries.executeQuery();
				while (rs.next()) {
					Date date = rs.getDate("DateTime");
					dateEntry.add(date);
				}
			} else {

				PreparedStatement entries = DBConnector.myConnection
						.prepareStatement("SELECT DateTime FROM Login;");
				ResultSet rs = entries.executeQuery();
				while (rs.next()) {
					Date date = rs.getDate("DateTime");
					dateEntry.add(date);
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dateEntry;

	}

	/**
	 * Gets an array of dates given a range.
	 * 
	 * @param Knumber
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ArrayList<Date> getEntryRange(int Knumber, Date startDate,
			Date endDate) {
		ArrayList<Date> studentLogin = new ArrayList<Date>();
		try {
			if (Knumber == 0) {// When Knumber is null, we will select all
				// dates and add it to the date arraylist.
				PreparedStatement entries = DBConnector.myConnection
						.prepareStatement("SELECT DateTime FROM Login;");
				ResultSet rs = entries.executeQuery();
				while (rs.next()) {
					Timestamp timestamp = rs.getTimestamp("DateTime");
					Date date = new Date(timestamp.getTime());
					if (date.compareTo(startDate) >= 0
							&& date.compareTo(endDate) <= 0) {
						studentLogin.add(date);
					}
				}
			} else {
				PreparedStatement entries = DBConnector.myConnection
						.prepareStatement("SELECT DateTime FROM Login WHERE Knumber='"
								+ Knumber + "';");
				ResultSet rs = entries.executeQuery();
				while (rs.next()) {
					Timestamp timestamp = rs.getTimestamp("DateTime");
					Date date = new Date(timestamp.getTime());
					System.out.println(date + " : " + startDate);
					System.out.println(date + " : " + endDate);

					if (date.compareTo(startDate) >= 0
							&& date.compareTo(endDate) <= 0) {
						studentLogin.add(date);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("TOTAL: " + studentLogin.size());
		return studentLogin;
	}

	/**
	 * gets an array of dates based, last 2 weeks dates, based on students logins.
	 * 
	 * @param Knumber
	 * @return
	 */
	public ArrayList<Date> getParticipation(int Knumber) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// Todays Date
		String date = ZonedDateTime.now().format(
				DateTimeFormatter.ISO_LOCAL_DATE);
		// last 2 weeks date
		String date2 = ZonedDateTime.now().minusWeeks(2)
				.format(DateTimeFormatter.ISO_LOCAL_DATE);
		
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = formatter.parse(date);
			System.out.println(startDate);
			endDate = formatter.parse(date2);
			System.out.println(endDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return getEntryRange(Knumber, startDate, endDate);
	}

	/**
	 * test purpose.
	 * 
	 * @param Knumber
	 * @throws ParseException
	 * @throws SQLException
	 */
	public void addUsingDates(int Knumber, Timestamp date)
			throws ParseException, SQLException {
		PreparedStatement delete = DBConnector.myConnection
				.prepareStatement("INSERT INTO Login VALUE(NULL, '" + Knumber
						+ "', '" + date + "');");
		delete.executeUpdate();
	}
}
