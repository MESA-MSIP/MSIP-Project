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
				PreparedStatement createTable = DBConnector.myConnection
						.prepareStatement("CREATE TABLE IF NOT EXISTS Admin(Knumber INT NOT NULL, FirstName VARCHAR(35) NOT NULL,"
								+ "LastName VARCHAR(35) NOT NULL, pHash VARCHAR(512) NOT NULL, PRIMARY KEY(Knumber))");
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
		public void remove(int Knumber) {
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
		 * Deletes all rows from Admin table. 
		 */
		public void deleteAll(){
			try {
				PreparedStatement deletAll = DBConnector.myConnection.prepareStatement("DELETE FROM Admin");
				deletAll.executeUpdate();
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

			PreparedStatement adminInfo;
			try {
				adminInfo = DBConnector.myConnection
						.prepareStatement("SELECT * FROM Admin WHERE Knumber='"
								+ Knumber + "';");
				ResultSet rs = adminInfo.executeQuery();
				//TODO handle when admin is null.
				while (rs.next()) {
					admin = new Admin(rs.getString("FirstName"), rs.getString("LastName"), Integer.parseInt(rs.getString("Knumber")), rs.getString("pHash"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return admin;
		}
		
		/**
		 * Gets all the admins and stores it in an ArrayList of admins.
		 * @return
		 */
		public ArrayList<Admin> getAll(){
			ArrayList<Admin> allAdmins = new ArrayList<Admin>();
			Admin admin = null;
			PreparedStatement adminInfo;
			try {
				adminInfo = DBConnector.myConnection
						.prepareStatement("SELECT * FROM Admin;");
				
				ResultSet rs = adminInfo.executeQuery();
				while (rs.next()) {
					admin = new Admin(rs.getString("FirstName"), rs.getString("LastName"), Integer.parseInt(rs.getString("Knumber")), rs.getString("pHash"));
					allAdmins.add(admin);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return allAdmins;
			
		}
	}


