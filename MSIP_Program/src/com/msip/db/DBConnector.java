package com.msip.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	static Connection myConnection;

	/**
	 * Connects to the SQL database and creates the login table as soon as the
	 * database object has been created and executed.
	 */
	public DBConnector() {
		try {
			myConnection = (Connection) DriverManager.getConnection(Global.URL,
					Global.USERNAME, Global.PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		new DBConnector();
		AdminTable a = new AdminTable();
		a.add(512840, "Fernando", "Estevez", "123456789");
		a.modify(512840, "SoccerGod");
	    a.getInfo(512840);
		StudentTable student = new StudentTable();
		student.add(512840, "Fernando", "Estevez", "Computer Engineer");
		student.modify(512840, "Art");
		student.getInfo(512840);
	
	}

}
