package com.msip.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
	private static Connection myConnection;

	/**
	 * Connects to the SQL database and creates the login table as soon as the
	 * database object has been created and executed.
	 */
	public Database() {
		try {
			myConnection = (Connection) DriverManager.getConnection(Global.URL,
					Global.USERNAME, Global.PASSWORD);
			PreparedStatement insert = (PreparedStatement) myConnection
					.prepareStatement("CREAT loginTable IF NOT EXISTS(ID INT Not Null Auto_increment, "
							+ "K number INT Not Null, "
							+ "Date/Time DATETIME Not Null Default Now(), Primary Key(ID))");
			insert.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves login table to PDF.
	 */
	public File saveTable() {
		return null;

	}

	/**
	 * Deletes the login table.
	 */
	public static void deleteTable() {

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
