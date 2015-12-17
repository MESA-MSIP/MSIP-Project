package com.msip.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Login {
	/**
	 * Adds student/admin to login table.
	 * @param Knumber
	 */
	public static void add(int Knumber){
		try {
			PreparedStatement delete = DBConnector.myConnection.prepareStatement("INSERT INTO Login('" 
		+ Knumber +"', NOW());");
			delete.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes student/admin from login table
	 */
	public static void remove(int Knumber){
		try {
			PreparedStatement delete = DBConnector.myConnection.prepareStatement("DELETE FROM Admin WHERE Knumber='" 
		+ Knumber +"';");
			delete.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
}
