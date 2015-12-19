package com.msip.db;

import com.msip.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class AdminTable {
	/**
	 * Creates the AdminTable if it doesn't exist.
	 */
		public AdminTable() {
		
			try {
				PreparedStatement createTable = DBConnector.myConnection
						.prepareStatement("CREATE TABLE IF NOT EXISTS Admin(ID INT NOT NULL AUTO_INCREMENT, Knumber INT NOT NULL, FirstName VARCHAR(35) NOT NULL,"
								+ "LastName VARCHAR(35) NOT NULL, pHash VARCHAR(50) NOT NULL, PRIMARY KEY(ID))");
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
						.prepareStatement("INSERT INTO Admin VALUE(NULL, '" + Knumber
								+ "',  '" + firstName + "', '" + lastName + "', '"
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
			try {
				PreparedStatement updateTable = (PreparedStatement) DBConnector.myConnection
						.prepareStatement("UPDATE Admin SET pHash='"
								+ pHash + "' WHERE Knumber='" + Knumber + "';");
				updateTable.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return getInfo(Knumber);

		}
		/**
		 * Gets the current info of an admin.
		 * @param Knumber
		 * @return admin
		 */
		public Admin getInfo(int Knumber){
			Admin admin = null;

			PreparedStatement studentInfo;
			try {
				studentInfo = DBConnector.myConnection
						.prepareStatement("SELECT * FROM Admin WHERE Knumber='"
								+ Knumber + "';");
				ResultSet rs = studentInfo.executeQuery();
				//TODO handle when student is null.
				while (rs.next()) {
					admin = new Admin(rs.getString("FirstName"), rs.getString("LastName"), Integer.parseInt(rs.getString("Knumber")), rs.getString("phash"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return admin;
		}
	}


