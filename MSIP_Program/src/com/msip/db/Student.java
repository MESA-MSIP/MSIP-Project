package com.msip.db;

import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
	Student student = null;;
	/**
	 * Adds student to the student database.
	 * @param Knumber
	 * @param firstName
	 * @param lastName
	 */
	public static void add(int Knumber, String firstName, String lastName, String Major){
		try {
			PreparedStatement insert = (PreparedStatement) Database.myConnection.prepareStatement("INSERT INTO Student VALUE('" + Knumber 
					+"',  '"+ firstName+"','"+lastName+"', '"+Major+"');");
			insert.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes student from student database.
	 * @param Knumber
	 */
	public static void remove(int Knumber){
		try {
			PreparedStatement delete = Database.myConnection.prepareStatement("DELETE FROM Student WHERE Knumber='" 
		+ Knumber +"';");
			delete.executeUpdate();
		} catch(Exception e){
			
		}
	}
	
	/**
	 * Updates any student changes.
	 * @param Knumber Major
	 * @return student
	 */
	public Student modify(int Knumber, String Major){
		try {
			PreparedStatement updateTable = (PreparedStatement) Database.myConnection.prepareStatement("UPDATE student SET major='" +
		Major + "' WHERE Knumber='" + Knumber + "';");
			updateTable.executeUpdate();
			
//		PreparedStatement studentInfo = Database.myConnection.prepareStatement("SELECT * FROM student WHERE Knumber='" 
//				+ Knumber +"';");
//		ResultSet rs = studentInfo.executeQuery();
//		String s = "";
//			while(rs.next()){
//		s=rs.getString(Knumber);
//			s+= rs.getNString("major");
//			System.out.println(s);
//			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return student;
		
	}
	
}
