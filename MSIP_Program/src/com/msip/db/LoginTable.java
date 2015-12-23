package com.msip.db;

import java.sql.*;

import com.msip.model.LoginEntry;

/**
 * @author estev_000
 *
 */
public class LoginTable {
	
	/**
	 * Creates the LoginTable if it doesn't exist.
	 */
	public LoginTable(){
		PreparedStatement createLoginTable;
		try {
			createLoginTable = DBConnector.myConnection
					.prepareStatement("CREATE TABLE IF NOT EXISTS Login(ID INT Not Null Auto_increment, "
							+ "Knumber INT Not Null, "
							+ "DateTime DATETIME Not Null Default Now(), Primary Key(ID))");
			createLoginTable.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
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

	/**
	 * Gets the current info of a Student/Admin logged in to the loginTable.
	 * @param Knumber
	 * @return person
	 */
	public LoginEntry getInfo(int Knumber){
		LoginEntry person = null; //TODO should return a LoginEntry Object. Not a person. 
	  Date date = null;// Creates an SQL Date object.
		try {
			
			PreparedStatement studentInfo = DBConnector.myConnection
					.prepareStatement("SELECT * FROM student WHERE Knumber='"
							+ Knumber + "';");
			ResultSet rs = studentInfo.executeQuery();
			
			while (rs.next()) {
				date = Date.valueOf(rs.getString("DateTime"));
			person = new LoginEntry(date, Integer.parseInt(rs.getString("Knumber")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return person;

		
	}
}
