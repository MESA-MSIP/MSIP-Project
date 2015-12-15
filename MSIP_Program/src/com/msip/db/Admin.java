package com.msip.db;

//import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Admin {
	/**
	 * Adds new admin to the admin table.
	 * 
	 * @param Knumber
	 * @param firstName
	 * @param lastName
	 */
	public static void add(int Knumber, String firstName, String lastName) {
		//dB = new Database();
	
		try {
			PreparedStatement insert = (PreparedStatement) Database.myConnection.prepareStatement("INSERT INTO Admin VALUE('" + Knumber 
					+"',  '"+ firstName+"','"+lastName+"');");
			insert.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			PreparedStatement delete = Database.myConnection.prepareStatement("DELETE FROM Admin WHERE Knumber='" 
		+ Knumber +"';");
			delete.executeUpdate();
		} catch(Exception e){
			
		}

	}

	/**
	 * Modifies the admin.
	 * 
	 * @param name
	 * @return admin
	 */
	public Admin modify(int Knumber, String Major) {
		try {
			PreparedStatement updateTable = (PreparedStatement) Database.myConnection.prepareStatement("UPDATE student SET major='" +
		Major + "' WHERE Knumber='" + Knumber + "';");
			updateTable.executeUpdate();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
