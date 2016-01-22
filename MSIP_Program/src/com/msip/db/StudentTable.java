package com.msip.db;
import com.msip.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentTable {
	
	/**
	 * Creates the StudentTable if it doesn't exist.
	 */
	public StudentTable() {
		try {
			PreparedStatement createTable = DBConnector.myConnection
					.prepareStatement("CREATE TABLE IF NOT EXISTS Student(Knumber INT NOT NULL, FirstName VARCHAR(35) NOT NULL,"
							+ "LastName VARCHAR(35) NOT NULL, Major VARCHAR(35) NOT NULL, PRIMARY KEY(Knumber))");
			createTable.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Adds student to the student database.
	 * 
	 * @param Knumber
	 * @param firstName
	 * @param lastName
	 */
	public void add(int Knumber, String firstName, String lastName,
			String Major) {
		try {
			PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("INSERT INTO Student VALUE('" + Knumber
							+ "',  '" + firstName + "','" + lastName + "', '"
							+ Major + "');");
			insert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes student from student database.
	 * 
	 * @param Knumber
	 */
	public void remove(int Knumber) {
		try {
			PreparedStatement delete = DBConnector.myConnection
					.prepareStatement("DELETE FROM Student WHERE Knumber='"
							+ Knumber + "';");
			delete.executeUpdate();
		} catch (SQLException e) {

		}
	}
	
	/**
	 * Deletes all rows from Student table. 
	 */
	public void deleteAll(){
		try {
			PreparedStatement deletAll = DBConnector.myConnection.prepareStatement("DELETE FROM Student");
			deletAll.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates any student changes.
	 * 
	 * @param Knumber
	 *            Major
	 * @return student
	 */
	public Student modify(int Knumber, String Major) {
		try {
			PreparedStatement updateTable = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("UPDATE Student SET Major='" + Major
							+ "' WHERE Knumber='" + Knumber + "';");
			updateTable.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getInfo(Knumber);
	}
	/**
	 * Gets the current info of a Student.
	 * @param Knumber
	 * @return Student
	 */
	public Student getInfo(int Knumber){
		Student student = null;
		PreparedStatement studentInfo;
		try {
			studentInfo = DBConnector.myConnection
					.prepareStatement("SELECT * FROM Student WHERE Knumber='"
							+ Knumber + "';");
			
			ResultSet rs = studentInfo.executeQuery();
			while (rs.next()) {
				student = new Student(rs.getString("FirstName"), rs.getString("LastName"), Integer.parseInt(rs.getString("Knumber")), rs.getString("Major"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

}
