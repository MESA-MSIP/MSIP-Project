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
		a.add(123456, "Estevez", "Fernando", "123123");
		a.add(678910, "fer", "estevez", "456789");
		a.remove(512840);
		a.modify(123456, "987654321");
		a.getInfo(678910);
		a.deleteAll();
		
		StudentTable student = new StudentTable();
		student.add(512840, "Fernando", "Estevez", "Computer Engineering");
		student.add(512000, "Fernando", "Estevez", "Computer Science");
		student.add(123456, "Fernando", "Estevez", "Math");
		student.remove(512840);
		student.modify(512000, "Computer Engineering");
		student.getInfo(123456);
		
		LoginTable login = new LoginTable();
		login.add(123456);
		login.add(123456);
		login.add(001000);
		login.add(512840);
		login.add(001000);
		login.add(111111);
		login.add(123456);
		login.add(001000);
		login.add(678910);
		login.add(000000);
		login.add(345678);
		login.add(001000);
		login.add(987654);
		login.add(001000);
		login.getInfo(111111);
		login.getLoginEntry(123456);
	}

}
