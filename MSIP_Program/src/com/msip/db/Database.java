package com.msip.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Database {
	/**
	 * Connects to the MySQL Database
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		try {
			Connection myConnection = (Connection) DriverManager.getConnection(
					Global.URL, Global.USERNAME, Global.PASSWORD);
			return myConnection;
		} catch (Exception e) {
			System.out.println(e);

		}
		return null;
	}

	/**
	 * Creates the login table.
	 */
	public static void createTable() {

	}

	/**
	 * Inserts student into login table. probably will change the parameter to
	 * K#.
	 * 
	 * @param firstName
	 * @param lastName
	 */
	public static void insertToTable(String firstName, String lastName) {
		try {
			Connection myConnection = getConnection();
			// Inserts a first and last namer to the table using a SQL cmd.
			// Don't forget to put single quotes around your string. Otherwise
			// you will get an error.
			PreparedStatement insert = (PreparedStatement) myConnection
					.prepareStatement("INSERT INTO myTable VALUE( NULL, '"
							+ firstName + "','" + lastName + "', NOW());");
			// Executes the cmd.
			insert.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Saves login table before shutting the computer down.
	 */
	public static void saveTable() {

	}

	/**
	 * Deletes the login table to have a fresh new start in the morning.
	 */
	public static void deleteTable() {

	}

	/**
	 * Adds MESA Student to the student Table.
	 */
	public static void addStudent() {

	}

	/**
	 * Deletes a MESA Student from the student table.
	 */
	public static void deleteStudent() {

	}

	/**
	 * Adds a MESA Admin to the admin Table.
	 */
	public static void addAdmin() {

	}

	/**
	 * Deletes a MESA Admin from the admin table.
	 */
	public static void deleteAdmin() {

	}

	/**
	 * Gets info from a specific slot and returns that info
	 * 
	 * @return info at a given spot.
	 */
	public String getLoginTableEntry() {
		return null;

	}

	/**
	 * Writes login table to a file.
	 * 
	 * @return file of todays login.
	 */
	public File getDailyReport() {
		return null;

	}

	/**
	 * Creates a report based on users preference.
	 * 
	 * @return file report of MESA usage.
	 */
	public File getReport() {
		return null;

	}

}
