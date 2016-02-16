package com.msip.db;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;

import com.msip.external.Utility;

public class DBConnector {
	static Connection myConnection;

	/**
	 * Connects to the SQL database and creates the login table as soon as the
	 * database object has been created and executed.
	 */
	public DBConnector() {
		try {
			myConnection = (Connection) DriverManager.getConnection(Global.URL, Global.USERNAME, Global.PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("\n" + "You did not Sign in correctly or you aren't using 'MesaDB' database" + "\n");
		}
	}

	public static void main(String[] args) throws SQLException, ParseException, NoSuchAlgorithmException {
		new DBConnector();
		AdminTable admin = new AdminTable();
		admin.deleteAll();
		admin.add(11111111, "Juan", "Zepeda", Utility.getHashedPassword("1"));
		admin.add(22222222, "Cynthia", "Barajas", Utility.getHashedPassword("2"));
		admin.add(33333333, "Fernando", "Estevez", Utility.getHashedPassword("3"));
		// admin.getAll();

		StudentTable student = new StudentTable();
		student.deleteAll();
		student.add(33333333, "Fernando", "Estevez", "Computer Engineering");
		student.add(44444444, "Christian", "Martinez", "Computer Science");
		student.add(55555555, "Celina", "Lazaro", "Computer Engineering");
		student.add(66666666, "Jorge", "Pantaleon", "Electrcal Engineering");
		student.add(77777777, "Daryl", "Delgado", "Electrical Engineering");
		// student.getAll();

		LoginTable login = new LoginTable();
		login.deleteAll();

		for (int i = 0; i < 500; i++) {
			login.addUsingDates(33333333, getRandomDate());
		}

		for (int i = 0; i < 500; i++) {
			login.addUsingDates(44444444, getRandomDate());
		}

		for (int i = 0; i < 5000; i++) {
			login.addUsingDates(55555555, getRandomDate());
		}
		for (int i = 0; i < 500; i++) {
			login.addUsingDates(66666666, getRandomDate());
		}

		for (int i = 0; i < 500; i++) {
			login.addUsingDates(77777777, getRandomDate());
		}
	}

	private static Timestamp getRandomDate() {
		long offset = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("2016-12-01 00:00:00").getTime();
		long diff = end - offset + 1;
		return new Timestamp(offset + (long) (Math.random() * diff));
	}
}
