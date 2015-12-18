package com.msip.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.msip.model.Person;

public class LoginTable {
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
