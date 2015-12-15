package com.msip.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
	 static Connection myConnection;

	/**
	 * Connects to the SQL database and creates the login table as soon as the
	 * database object has been created and executed.
	 */
	public Database() {
		try {
			myConnection = (Connection) DriverManager.getConnection(Global.URL,
					Global.USERNAME, Global.PASSWORD);
			PreparedStatement createTable = (PreparedStatement) myConnection
					.prepareStatement("CREATE TABLE IF NOT EXISTS LoginTable(ID INT Not Null Auto_increment, "
							+ "Knumber INT Not Null, "
							+ "DateTime DATETIME Not Null Default Now(), Primary Key(ID))");
			createTable.executeUpdate();

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
		try {
			PreparedStatement delete = myConnection.prepareStatement("DROP TABLE loginTable;");
			delete.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
