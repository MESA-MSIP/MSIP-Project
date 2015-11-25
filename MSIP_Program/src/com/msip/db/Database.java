package com.msip.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Database {
	public static Connection getConnection() {
		try {
			// Connects to your mySQL database using the url, username, and
			// password.
			Connection myConnection = (Connection) DriverManager.getConnection(
					Global.URL, Global.USERNAME, Global.PASSWORD);
			return myConnection;
		} catch (Exception e) {
			System.out.println(e);

		}
		return null;
	}

	public static void createTable() {

	}

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

	public static void saveTable() {

	}

	public static void deleteTable() {

	}

	public static void addStudent() {

	}

	public static void deleteStudent() {

	}

	public static void addAdmin() {

	}

	public static void deleteAdmin() {

	}

	public static void getLoginTableEntry() {

	}

	public static void getDailyReport() {

	}

	public static void getReport() {

	}

}
