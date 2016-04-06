package com.msip.db;

import com.msip.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminTable {
	/**
	 * Creates the AdminTable if it doesn't exist.
	 */
	public AdminTable() {

		try {
			PreparedStatement createTable = DBConnector.myConnection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS Admin(Knumber INT NOT NULL, FirstName VARCHAR(35) NOT NULL,"
							+ "LastName VARCHAR(35) NOT NULL, pHash VARCHAR(512) NOT NULL, PRIMARY KEY(Knumber))");
			createTable.executeUpdate();
			System.out.println("Successfully created Admin table");

		} catch (SQLException e) {
			System.out.println("Failed to create admin table" + "\n");
			e.printStackTrace();
		}
	}

	/**
	 * Adds new admin to the admin table.
	 * 
	 * @param Knumber
	 * @param firstName
	 * @param lastName
	 * @throws SQLException
	 */
	public void add(int Knumber, String firstName, String lastName, String password) throws SQLException {

		PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
				.prepareStatement("INSERT INTO Admin VALUE('" + Knumber + "',  '" + firstName + "', '" + lastName
						+ "', '" + password + "');");
		insert.executeUpdate();
	}

	/**
	 * Removes admin from admin table.
	 * 
	 * @param Knumber
	 * @throws SQLException 
	 */
	public void remove(int Knumber) throws SQLException {
		PreparedStatement delete = DBConnector.myConnection
				.prepareStatement("DELETE FROM Admin WHERE Knumber='" + Knumber + "';");
		delete.executeUpdate();
	}

	/**
	 * Deletes all rows from Admin table.
	 */
	public void deleteAll() {
		try {
			PreparedStatement deletAll = DBConnector.myConnection.prepareStatement("DELETE FROM Admin");
			deletAll.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Modifies the admin.
	 * 
	 * @param Knumber,
	 *            pHash
	 * @return admin
	 * @throws SQLException
	 */
	public Admin modify(int Knumber, String pHash) throws SQLException {
		PreparedStatement updateTable = (PreparedStatement) DBConnector.myConnection
				.prepareStatement("UPDATE Admin SET pHash='" + pHash + "' WHERE Knumber='" + Knumber + "';");
		updateTable.executeUpdate();
		return getInfo(Knumber);
	}

	/**
	 * Returns the phash based on the knumber.
	 * @param Knumber
	 */
	public String getPhash(int Knumber){
		String myPhash = null;
		try {
			PreparedStatement pHashRetrever = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("SELECT pHash FROM Admin WHERE Knumber='" + Knumber + "';");
			ResultSet rs = pHashRetrever.executeQuery();
			while(rs.next()){
				myPhash = rs.getString("pHash");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return myPhash;
		}
	
	/**
	 * Gets the current info of an admin.
	 * 
	 * @param Knumber
	 * @return admin
	 * @throws SQLException
	 */
	public Admin getInfo(int Knumber) throws SQLException {
		Admin admin = null;

		PreparedStatement adminInfo = DBConnector.myConnection
				.prepareStatement("SELECT * FROM Admin WHERE Knumber='" + Knumber + "';");
		ResultSet rs = adminInfo.executeQuery();
		// TODO handle when admin is null.
		while (rs.next()) {
			admin = new Admin(rs.getString("FirstName"), rs.getString("LastName"),
					Integer.parseInt(rs.getString("Knumber")), rs.getString("pHash"));
		}

		return admin;
	}

	/**
	 * Gets all the admins and stores it in an ArrayList of admins.
	 * 
	 * @return
	 */
	public ArrayList<Admin> getAll() {
		ArrayList<Admin> allAdmins = new ArrayList<Admin>();
		Admin admin = null;
		PreparedStatement adminInfo;
		try {
			adminInfo = DBConnector.myConnection.prepareStatement("SELECT * FROM Admin;");

			ResultSet rs = adminInfo.executeQuery();
			while (rs.next()) {
				admin = new Admin(rs.getString("FirstName"), rs.getString("LastName"),
						Integer.parseInt(rs.getString("Knumber")), rs.getString("pHash"));
				allAdmins.add(admin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allAdmins;

	}
}
