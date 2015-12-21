package com.msip.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.msip.model.Person;

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
	public Person getInfo(int Knumber){
		Person person = null;
		try {
			PreparedStatement studentInfo = DBConnector.myConnection
					.prepareStatement("SELECT * FROM student WHERE Knumber='"
							+ Knumber + "';");
			ResultSet rs = studentInfo.executeQuery();
			
			while (rs.next()) {
			person = new Person(rs.getString("FirstName"), rs.getString("LastName"), Integer.parseInt(rs.getString("Knumber")), null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return person;

		
	}
}
