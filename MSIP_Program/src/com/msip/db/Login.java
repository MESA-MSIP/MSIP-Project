package com.msip.db;

import java.sql.PreparedStatement;

public class Login {
	/**
	 * Adds student/admin to login table.
	 * @param Knumber
	 */
	public static void add(int Knumber){
		try {
			PreparedStatement delete = Database.myConnection.prepareStatement("INSERT INTO Login('" 
		+ Knumber +"', NOW());");
			delete.executeUpdate();
		} catch(Exception e){
			
		}
	}
	
	/**
	 * Removes student/admin from login table
	 */
	public static void remove(int Knumber){
		try {
			PreparedStatement delete = Database.myConnection.prepareStatement("DELETE FROM Admin WHERE Knumber='" 
		+ Knumber +"';");
			delete.executeUpdate();
		} catch(Exception e){
			
		}
	}
}
