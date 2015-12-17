package com.msip.db;

import com.msip.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin  {


	public Admin() {
	
		try {
			PreparedStatement createTable = DBConnector.myConnection
					.prepareStatement("CREATE TABLE IF NOT EXISTS Admin(Knumber INT NOT NULL, FirstName VARCHAR(35) NOT NULL,"
							+ "LastName VARCHAR(35) NOT NULL, pHash VARCHAR(50) NOT NULL, PRIMARY KEY(Knumber))");
			createTable.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds new admin to the admin table.
	 * 
	 * @param Knumber
	 * @param firstName
	 * @param lastName
	 */
	public void add(int Knumber, String firstName, String lastName,
			String password) {

		try {
			PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("INSERT INTO Admin VALUE('" + Knumber
							+ "',  '" + firstName + "','" + lastName + "', '"
							+ password + "');");
			insert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes admin from admin table.
	 * 
	 * @param Knumber
	 */
	public static void remove(int Knumber) {
		try {
			PreparedStatement delete = DBConnector.myConnection
					.prepareStatement("DELETE FROM Admin WHERE Knumber='"
							+ Knumber + "';");
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	/**
	 * Modifies the admin.
	 * @param Knumber, pHash
	 * @return admin
	 */
	public Admin modify(int Knumber, String pHash) {
		Admin admin = null;
		try {
			PreparedStatement updateTable = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("UPDATE student SET pHash='"
							+ pHash + "' WHERE Knumber='" + firstName + "';");
			updateTable.executeUpdate();

			PreparedStatement studentInfo = DBConnector.myConnection
					.prepareStatement("SELECT * FROM student WHERE Knumber='"
							+ Knumber + "';");
			
			ResultSet rs = studentInfo.executeQuery();
			
			while (rs.next()) {
				admin = new Admin(rs.getNString("FirstName"), rs.getNString("LastName"), rs.getNString("Knumber"), rs.getNString("pHash"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;

	}
}
